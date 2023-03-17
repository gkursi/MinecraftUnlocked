package dev.qweru.mcu.mods;

import dev.qweru.mcu.util.Chat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static dev.qweru.mcu.util.Chat.sendMessage;

public class ChatHandler {

    public static boolean doPacketDumps = false;
    public static boolean doAnimationCancel = false;
    public static boolean logEntities = false;
    public static boolean chatInfo = false;

    protected static MinecraftClient mc = MinecraftClient.getInstance();

    private static List<String> parse(String text, String detectCommand){
        return List.of(text.replace(detectCommand, "").split(" "));
    }

    public static boolean handle(String text){
        if(text.startsWith(".")){
            if(text.startsWith(".dumpPackets")){
                List<String> args = parse(text, ".dumpPackets");
                try {
                    doPacketDumps=!doPacketDumps;
                    if(doPacketDumps){
                        sendMessage("Packet dumps enabled in .minecraft\\config\\packetDump.txt");
                    }
                    else sendMessage("Packet dumping disabled.");
                } catch (Exception e){
                    sendMessage("An error occurred while executing code.");
                }

            } else if (text.startsWith(".help")) {
                sendMessage("Help utility");
                sendMessage(".dumpPackets - dumps all of the packets sent by the server in a text file");
                sendMessage(".cancelAnimations - cancels animations sent from server");
                sendMessage(".entityInfo - shows entity info in chat (also players)");
                sendMessage(".chatInfo - gives you a lot of info in your chat");
                sendMessage(".sendPacket < move | ... > <json object with args> - sends packet to server");
                sendMessage(".editBook <hotbar slot> <text file path> - edits the book in the specified inventory slot. To separate text between pages, use ;;.");
            } else if (text.startsWith(".cancelAnimations")) {
                doAnimationCancel=!doAnimationCancel;
                if(doAnimationCancel) sendMessage("Enabled animation cancelling");
                else sendMessage("Disabled animation cancelling");
            } else if (text.startsWith(".entityinfo")) {
                logEntities=!logEntities;
                if(logEntities) sendMessage("Enabled entity stuff (chat info has to be enabled)");
                else sendMessage("Disabled entity stuff");
            } else if (text.startsWith(".chatInfo")) {
                chatInfo=!chatInfo;
                if(chatInfo) sendMessage("Enabled more chat info");
                else sendMessage("Disabled more chat info");
            } else if (text.startsWith(".sendPacket")) {
                List<String> args = parse(text, ".sendPacket");

            } else if (text.startsWith(".editBook")) {
                try {
                    List<String> args = parse(text, ".sendPacket");
                    StringBuilder data = new StringBuilder();
                    try {
                        File file = new File(args.get(2));
                        Scanner reader = new Scanner(file);
                        while (reader.hasNextLine()) {
                            data.append(reader.nextLine());
                        }
                        reader.close();
                    } catch (FileNotFoundException e) {
                        Chat.sendErrorMessage("An error occurred while reading file. (FileNotFoundException) ");
                    }
                    List<String> pages = List.of(data.toString().split(";;"));
                    BookUpdateC2SPacket book = new BookUpdateC2SPacket(Integer.parseInt(args.get(1)), pages, Optional.empty());
                    Chat.sendMessage("Writing to book...");
                    if(mc.player!=null) mc.player.networkHandler.sendPacket(book);
                    else Chat.sendErrorMessage("Player object is null. How did you get here?");
                } catch (Exception e) {
                    Chat.sendErrorMessage("An error occurred. Invalid usage? ("+e.getMessage()+")");
                }
            } else {
                sendMessage("Invalid command! Type .help for help");
            }
            return true;
        }
        return false;
    }
}
