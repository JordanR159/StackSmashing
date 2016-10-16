import org.joml.Vector4d;

public class Beam extends Block
{
	private boolean toRight;
	private Player source;
	private Player target;

	public Beam(World world, Player s, Player t)
	{
		super(world, s.getPosX(), s.getPosY(), t.getPosX(), t.getPosY());

		source = s;
		target = t;

		pos.x += s.getSize() / 2;
		pos.y += s.getSize() / 2;
		//pos.z += t.getSize() / 2;
		//pos.w += t.getSize() / 2;
		pos.w = 0;

		if(s.getPosX() > t.getPosX())
		{
			toRight = false;
			pos.z = pos.x;
			pos.x = 0;
		}
		else
		{
			toRight = true;
			pos.z = world.getWidth();
		}
	}

	public boolean trace()
	{
		Block block;

		for(int i = 0; i < worldObj.getBlocks().size(); i++)
		{
			block = worldObj.getBlocks().get(i);

			if(CollisionHelper.colliding(pos, block.pos))
			{
				if(toRight)
				{
					pos.z = block.pos.x;
				}
				else
				{
					pos.x = block.pos.z;
				}
			}
		}
		
		pos.z -= pos.x;
		

		System.out.println(pos.x + " " + pos.y + " " + pos.z + " " + pos.w);
		System.out.println(target.pos.x + " " + target.pos.y + " " + target.pos.z + " " + target.pos.w);
		
		if(CollisionHelper.colliding(pos, target.pos))
		{
			System.out.println("collision");
			return true;
		}
		
		return false;
	}

}
