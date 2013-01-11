package com.gmail.mikeundead;

import java.util.HashMap;
import java.util.Map;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HandlePlayerPrefix implements Listener
{
	private DatabaseHandler databaseHandler;
	private Ranks ranks;
	@SuppressWarnings("unused")
	private PvPTitles pvpTitles;
	
	public HandlePlayerPrefix(DatabaseHandler databaseHandler, Ranks ranks, PvPTitles pvpTitles) 
	{
		this.databaseHandler = databaseHandler;
		this.ranks = ranks;
		this.pvpTitles = pvpTitles;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) 
	{
		Player player = event.getPlayer();
		
		try
		{
			this.databaseHandler.FirstRun(player.getName());
		}
		catch (Exception e) 
		{
	        e.printStackTrace();
	    }
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) 
	{
		Player player = event.getPlayer();
		
		this.map.put(player.getName(), 0);
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event)
    {	
		String rank = null;
		
		this.databaseHandler.LoadPlayerFame(event.getPlayer().getName());
		this.databaseHandler.LoadConfig();

		rank = this.ranks.GetRank(this.databaseHandler.PlayerFame());

		if(rank != null && rank != "")
		{						
			String a = String.format(ChatColor.WHITE + "[" + this.databaseHandler.PrefixColor + rank + ChatColor.WHITE + "] ");
			String format = event.getFormat();
			event.setFormat(a + format);
		}
    }
	
	Map<String, Integer> map = new HashMap<String, Integer>();
	
	@EventHandler
	public void onKill(PlayerDeathEvent death)
	{			
		int kills = 0 ;
		
		if(death.getEntity().getKiller() instanceof Player)
		{		
			String killed = death.getEntity().getName();
			Player player = death.getEntity().getKiller();

			if(this.map.containsKey(player.getName()))
			{
				kills = this.map.get(player.getName());
			}
			if(this.map.containsKey(killed))
			{
				this.map.put(killed, 0);
			}
			
			this.databaseHandler.LoadPlayerFame(player.getName());
			int fame = this.databaseHandler.PlayerFame();
			
			if(!player.getName().equalsIgnoreCase(killed))
			{
				this.calculateFame(killed, player, fame, kills);
			}
			
			kills++;
			this.map.put(player.getName(), kills);
		}
	}
	
	private void calculateFame(String killed, Player player, int fame, int kills)
	{
		int a = this.databaseHandler.PlayerFame();

		if(kills == 0)
		{
			fame++;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 1 Fame.");
		}
		if(kills == 1)
		{
			fame = fame + 2;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 2 Fame.");
		}
		if(kills == 2)
		{
			fame = fame + 3;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 3 Fame.");
		}
		if(kills == 3)
		{
			fame = fame + 4;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 4 Fame.");
		}
		if(kills == 4)
		{
			fame = fame + 6;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 6 Fame.");
		}
		if(kills == 5)
		{
			fame = fame + 8;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 8 Fame.");
		}
		if(kills == 6)
		{
			fame = fame + 12;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 12 Fame.");
		}
		if(kills == 7)
		{
			fame = fame + 16;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 16 Fame.");
		}
		if(kills == 8)
		{
			fame = fame + 20;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 20 Fame.");
		}
		if(kills == 9)
		{
			fame = fame + 24;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 24 Fame.");
		}
		if(kills == 10)
		{
			fame = fame + 28;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and recieved 28 Fame.");
		}
		if(kills == 11)
		{
			fame = fame + 32;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 32 Fame.");
		}
		if(kills == 12)
		{
			fame = fame + 36;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 36 Fame.");
		}
		if(kills >= 13)
		{
			fame = fame + 40;
			player.sendMessage(ChatColor.GREEN + "You killed " + killed + " and received 40 Fame.");
		}
		
		this.databaseHandler.SavePlayerFame(player.getName(), fame);
		this.databaseHandler.LoadPlayerFame(player.getName());

		String currentRank = this.ranks.GetRank(a);
		String newRank = this.ranks.GetRank(fame);
		
		if(!currentRank.equalsIgnoreCase(newRank))
		{
			player.sendMessage(ChatColor.GREEN + "Congrats! You are now a " + newRank);
		}
	}
}