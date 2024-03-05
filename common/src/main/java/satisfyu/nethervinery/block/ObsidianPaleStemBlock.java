package satisfyu.nethervinery.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.block.grape.GrapeType;
import satisfyu.vinery.block.stem.PaleStemBlock;
import satisfyu.vinery.block.stem.StemBlock;
import satisfyu.vinery.config.VineryConfig;
import satisfyu.vinery.item.GrapeBushSeedItem;
import satisfyu.vinery.registry.GrapeTypeRegistry;

import java.util.List;
import java.util.Random;

public class ObsidianPaleStemBlock extends StemBlock {
    private static final VoxelShape PALE_SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);

    public ObsidianPaleStemBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(GRAPE, GrapeTypeRegistry.NONE)).setValue(AGE, 0));
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return PALE_SHAPE;
    }

    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = this.defaultBlockState();
        Level world = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        return blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos()) ? blockState : null;
    }

    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        if (livingEntity instanceof Player player) {
            if (itemStack != null && (player.isCreative() || itemStack.getCount() >= 2) && level.getBlockState(blockPos.below()).getBlock() != this && blockPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockPos.above()).canBeReplaced()) {
                level.setBlock(blockPos.above(), this.defaultBlockState(), 3);
                itemStack.shrink(1);
            }
        }

    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (hand == InteractionHand.OFF_HAND) {
            return super.use(state, world, pos, player, hand, hit);
        } else {
            int age = (Integer)state.getValue(AGE);
            if (age > 0 && player.getItemInHand(hand).getItem() == Items.SHEARS) {
                if (age > 2) {
                    this.dropGrapes(world, state, pos);
                }

                this.dropGrapeSeeds(world, state, pos);
                world.setBlock(pos, this.withAge(state, 0, GrapeTypeRegistry.NONE), 3);
                world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_BREAK, SoundSource.AMBIENT, 1.0F, 1.0F);
                return InteractionResult.sidedSuccess(world.isClientSide);
            } else {
                ItemStack stack = player.getItemInHand(hand);
                Item var10 = stack.getItem();
                if (var10 instanceof GrapeBushSeedItem) {
                    GrapeBushSeedItem seed = (GrapeBushSeedItem)var10;
                    if (this.hasTrunk(world, pos) && age == 0 && !seed.getType().isLattice()) {
                        world.setBlock(pos, this.withAge(state, 1, seed.getType()), 3);
                        if (!player.isCreative()) {
                            stack.shrink(1);
                        }

                        world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PLACE, SoundSource.AMBIENT, 1.0F, 1.0F);
                        return InteractionResult.SUCCESS;
                    }
                }

                return super.use(state, world, pos, player, hand, hit);
            }
        }
    }

    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            if ((Integer)state.getValue(AGE) > 0) {
                this.dropGrapeSeeds(world, state, pos);
            }

            if ((Integer)state.getValue(AGE) > 2) {
                this.dropGrapes(world, state, pos);
            }

            world.destroyBlock(pos, true);
        }

    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        Random rand = new Random();
        if (rand.nextInt(100) + 1 <= ((VineryConfig)VineryConfig.DEFAULT.getConfig()).grapeGrowthSpeed()) {
            int i;
            if (!this.isMature(state) && this.hasTrunk(world, pos) && (Integer)state.getValue(AGE) > 0 && world.getRawBrightness(pos, 0) >= 9 && (i = (Integer)state.getValue(AGE)) < 4) {
                world.setBlock(pos, this.withAge(state, i + 1, (GrapeType)state.getValue(GRAPE)), 2);
            }

            super.randomTick(state, world, pos, random);
        }
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.below()).isRedstoneConductor(world, pos) || world.getBlockState(pos.below()).getBlock() == this;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }


    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.vinery.stem.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}