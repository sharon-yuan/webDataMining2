/*
 * Copyright (C) 2014 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package cn.edu.hfut.dmic.webcollector.util;

public class Config {
    public static String DEFAULT_USER_AGENT="Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:36.0) Gecko/20100101 Firefox/36.0";
    public static int MAX_RECEIVE_SIZE = 1000 * 1000;
    public static long requestMaxInterval=1000*60*2;
    public static int retry=3;
    public static long WAIT_THREAD_END_TIME=1000*60;

    public static int MAX_REDIRECT=2;
    
     public static int TIMEOUT_CONNECT = 3000;
     public static int TIMEOUT_READ = 10000;
     public static int MAX_RETRY=20;

}
