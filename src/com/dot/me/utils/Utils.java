/*This file is part of OpenBus project.
*
*OpenBus is free software: you can redistribute it and/or modify
*it under the terms of the GNU General Public License as published by
*the Free Software Foundation, either version 3 of the License, or
*(at your option) any later version.
*
*OpenBus is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*GNU General Public License for more details.
*
*You should have received a copy of the GNU General Public License
*along with OpenBus. If not, see <http://www.gnu.org/licenses/>.
*
* Author: Caio Lima
* Date: 30 - 06 - 2013
*/
package com.dot.me.utils;

import java.io.UnsupportedEncodingException;

import android.net.Uri;

public class Utils {

	public static Uri createURIToLink(String link) throws UnsupportedEncodingException{
		return Uri.parse(link);
		//return Uri.parse("dot_link://web?url="+URLEncoder.encode(link, "UTF-8"));
	}
	
}
