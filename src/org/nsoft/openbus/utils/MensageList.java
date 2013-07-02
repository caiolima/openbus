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
package org.nsoft.openbus.utils;

import java.util.Vector;

import org.nsoft.openbus.model.Mensagem;


import android.os.Parcel;
import android.os.Parcelable;

public class MensageList implements Parcelable{
	private int mData;
	//private int mData;
	private Vector<Mensagem> mensagemList=new Vector<Mensagem>();
	
	public MensageList(Vector<Mensagem> list){
    	mensagemList=list;
    }
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<MensageList> CREATOR
            = new Parcelable.Creator<MensageList>() {
        @Override
		public MensageList createFromParcel(Parcel in) {
            return new MensageList(in);
        }

        @Override
		public MensageList[] newArray(int size) {
            return new MensageList[size];
        }
    };
    
    private MensageList(Parcel in) {
        mData = in.readInt();
    }
    
    



	public Vector<Mensagem> getMensagemList() {
		return mensagemList;
	}

	public void setMensagemList(Vector<Mensagem> mensagemList) {
		this.mensagemList = mensagemList;
	}

}
