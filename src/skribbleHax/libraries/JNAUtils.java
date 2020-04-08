package skribbleHax.libraries;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.W32APIOptions;


public class JNAUtils {
	public static void main(String[] args) throws InterruptedException {
		final String tosend = "notepad";
		
		HWND window = getFromName(tosend).getHWND();
		
		sendWindow(window, "zap!");
	}
	public static User32 USER = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
	
	public static void sendWindow(HWND window, String str) {
		for(char c : str.toCharArray()) sendWindow(window, c);
	}
	public static void sendWindow(HWND window, char c) {
		for(HWND w : getAllChildren(window)) {
			sendWindowDirect(w, c);
		}
		sendWindowDirect(window, c);
	}
	public static void sendWindowDirect(HWND w, char c) {
		int keycode = KeyEvent.getExtendedKeyCodeForChar(c);
		
		//System.out.println(keycode+" | "+USER.MapVirtualKey(keycode, 0)+" | "+(int) c+" | ");
		
		WinDef.WPARAM wparam = new WinDef.WPARAM(keycode);
		WinDef.LPARAM lparam = new WinDef.LPARAM(1 + (USER.MapVirtualKey(keycode, 0) << 16));
		
		USER.SendMessageA(w, WinUser.WM_KEYDOWN, wparam, lparam);
		try {Thread.sleep(10);} catch(Exception e) {}
		USER.SendMessageA(w, WinUser.WM_CHAR, new WinDef.WPARAM((int) c), lparam); //wparam wrong
		try {Thread.sleep(10);} catch(Exception e) {}
		USER.SendMessageA(w, WinUser.WM_KEYUP, wparam, lparam);
		try {Thread.sleep(10);} catch(Exception e) {}
		//lparam = new WinDef.LPARAM(1 + (USER.MapVirtualKey(keycode, 0) << 16) + (1 << 30) + (1 << 31));
	}
	public static void clickWindow(HWND window, int mouseButton) {
		clickWindow(window, mouseButton, (int)(Math.random()*500), (int)(Math.random()*500));
	}
	public static void clickWindow(HWND window, int mouseButton, int x, int y) {
		for(HWND w : getAllChildren(window)) {
			clickWindowDirect(w, 1,x,y);
		}
		clickWindowDirect(window, 1,x,y);
	}
	private static final int WM_LBUTTON = 0x0201;
	private static final int WM_MBUTTON = 0x0207;
	private static final int WM_RBUTTON = 0x0204;
	public static void clickWindowDirect(HWND window, int mouseButton, int x, int y) {
		long xy = x + (y << 16);//x + (y << 16)
		
		WinDef.WPARAM wparam = new WinDef.WPARAM(xy);
		WinDef.LPARAM lparam = new WinDef.LPARAM(0);
		
		int buttondown = WM_LBUTTON;
		if(mouseButton == 2) buttondown = WM_MBUTTON;
		if(mouseButton == 3) buttondown = WM_RBUTTON;
		
		USER.SendMessageA(window, buttondown, wparam, lparam);
		try {Thread.sleep(10);} catch(Exception e) {}
		USER.SendMessageA(window, buttondown+1, wparam, lparam);
		try {Thread.sleep(10);} catch(Exception e) {}
	}
	public static DesktopWindow getFromName(String name) {
		List<DesktopWindow> windows = WindowUtils.getAllWindows(false);
		
		//first simply scan the window titles
		for(DesktopWindow w : windows) {
			if(w.getTitle().strip().toLowerCase().contains(name.strip().toLowerCase()))
				return w;
		}
		//if no matching title is found, scan files
		for(DesktopWindow w : windows) {
			if(w.getFilePath().strip().toLowerCase().contains(name.strip().toLowerCase()))
				return w;
		}
		//return null if not found
		return null;
	}
	public static List<HWND> getAllChildren(final HWND parent){
		final List<HWND> out = new ArrayList<HWND>();
		com.sun.jna.platform.win32.WinUser.WNDENUMPROC callback = new com.sun.jna.platform.win32.WinUser.WNDENUMPROC() {
			//int count = 0;
			public boolean callback(HWND hWnd, Pointer data) {
				/*char[] windowText = new char[512];
                USER.GetWindowText(parent, windowText, 512);
                String wText = Native.toString(windowText);
                wText = (wText.isEmpty()) ? "" : " text: " + wText;
                System.out.println("Found window " + hWnd + ", total " + (++count) + wText);*/
                out.add(hWnd);
                return true;
			}
		};
		Pointer data = new Pointer(1000); //i have no idea what to send here. seems to be linked to how many windows max are found. 1000 is big enough
		USER.EnumChildWindows(parent, callback, data);
		return out;
	}
	//https://stackoverflow.com/questions/17255549/how-can-i-get-the-keyboard-scan-code-in-java
	final public static Integer getScancodeFromKeyEvent(final KeyEvent keyEvent) {

	    Integer ret;
	    Field field;

	    try {
	        field = KeyEvent.class.getDeclaredField("scancode");
	    } catch (NoSuchFieldException nsfe) {
	        System.err.println("ATTENTION! The KeyEvent object does not have a field named \"scancode\"! (Which is kinda weird.)");
	        nsfe.printStackTrace();
	        return null;
	    }

	    try {
	        field.setAccessible(true);
	    } catch (SecurityException se) {
	        System.err.println("ATTENTION! Changing the accessibility of the KeyEvent class' field \"scancode\" caused a security exception!");
	        se.printStackTrace();
	        return null;
	    }

	    try {
	        ret = (int) field.getLong(keyEvent);
	    } catch (IllegalAccessException iae) {
	        System.err.println("ATTENTION! It is not allowed to read the field \"scancode\" of the KeyEvent instance!");
	        iae.printStackTrace();
	        return null;
	    }

	    return ret;
	}
}
//https://stackoverflow.com/questions/36522039/difference-between-numpad-9-key-and-g-key-when-using-sendmessage
interface User32 extends com.sun.jna.platform.win32.User32
{
    int MapVirtualKey(int ucode, int uMapType);
    void SendMessageA(HWND handle, int message, WinDef.WPARAM wparam, WinDef.LPARAM lparam);
}
