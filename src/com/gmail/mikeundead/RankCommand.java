package com.gmail.mikeundead;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor 
{
	private DatabaseHandler databaseHandler;
	private Ranks ranks;

	public RankCommand(DatabaseHandler databaseHandler, Ranks ranks) 
	{
		this.databaseHandler = databaseHandler;
		this.ranks = ranks;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) 
	{
		Player player = null;
		if (sender instanceof Player) 
		{
			player = (Player) sender;
	    }
		if (cmd.getName().equalsIgnoreCase("rank"))
		{
			this.HandleRankCmd(player);
		}
        if (args.length > 0) 
        {
        	player.sendMessage(ChatColor.RED + "Too many arguments!");
        }

		return true;
	}
	
	private void HandleRankCmd(Player player)
	{
		this.databaseHandler.LoadPlayerFame(player.getName());
		this.databaseHandler.LoadConfig();
		int fame = this.databaseHandler.PlayerFame();
		String rank = this.ranks.GetRank(fame);
		int rankup = this.ranks.FameToRankUp();
        String tag = this.databaseHandler.getTag();
		
		if(rank == "")
		{
			player.sendMessage("Rank: none");
		}
		else
		{
			player.sendMessage("Rank: " + rank);
		}
		
		player.sendMessage(tag + ": " + fame);

		if(rankup == 999999)
		{
			player.sendMessage("You are max ranked.");
		}
		else
		{
			player.sendMessage("Rankup: " + rankup);
		}
	}
}
