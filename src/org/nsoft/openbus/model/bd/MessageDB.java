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
package org.nsoft.openbus.model.bd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;
import org.nsoft.openbus.command.OpenTwitterStatusAction;
import org.nsoft.openbus.model.Message;
import org.nsoft.openbus.utils.Menssage;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class MessageDB extends Dao {

	protected MessageDB(Context ctx) {
		super(ctx);
	}

	protected int insert(Message m) {

		ContentValues values = new ContentValues();
		values.put(DataBase.MESSAGE_ID, m.getIdMensagem());
		values.put(DataBase.MESSAGE_TEXT, m.getMensagem());
		values.put(DataBase.MESSAGE_USER, m.getNome_usuario());
		values.put(DataBase.MESSAGE_IMAGE_URL, m.getImagePath().toString());
		values.put(DataBase.MESSAGE_TYPE, m.getTipo());
		values.put(DataBase.MESSAGE_DATE, m.getData().getTime());
		values.put(DataBase.MESSAGE_ID_USER, m.getIdUser());
		values.put(DataBase.MESSAGE_ADD_CONTENT, m.getAddtions().toString());
		long r = -1;
		try {
			r = db.getDB().insert(DataBase.TB_MESSAGE, null, values);
		} catch (Exception e) {

		}
		if (r == -1)
			return Menssage.ERRO;
		else
			return Menssage.SUCCESS;
	}

	protected void update(Message m) {
		ContentValues values = new ContentValues();

		values.put(DataBase.MESSAGE_TEXT, m.getMensagem());
		values.put(DataBase.MESSAGE_USER, m.getNome_usuario());
		values.put(DataBase.MESSAGE_IMAGE_URL, m.getImagePath().toString());
		values.put(DataBase.MESSAGE_DATE, m.getData().getTime());
		values.put(DataBase.MESSAGE_ID_USER, m.getIdUser());
		values.put(DataBase.MESSAGE_ADD_CONTENT, m.getAddtions().toString());

		db.getDB()
				.update(DataBase.TB_MESSAGE,
						values,
						DataBase.MESSAGE_ID + "=? and "
								+ DataBase.MESSAGE_TYPE + "=?",
						new String[] { m.getIdMensagem(),
								Integer.toString(m.getTipo()) });
	}

	protected Vector<Message> getAllMensagens() throws MalformedURLException {
		try {
			Cursor c = db.getDB().query(DataBase.TB_MESSAGE, null, null, null,
					null, null, null);

			Vector<Message> mensagens = new Vector<Message>();
			for (int i = 0; i < c.getCount(); i++) {
				c.moveToPosition(i);
				Message mensagem = createMessage(c);

				mensagens.add(mensagem);

			}
			c.close();
			Collections.sort(mensagens);
			return mensagens;
		} catch (Exception e) {

			return null;
		}

	}

	protected Message getOne(String id, int type) {

		Cursor c = db.getDB().query(
				DataBase.TB_MESSAGE,
				null,
				DataBase.MESSAGE_ID + "=? and " + DataBase.MESSAGE_TYPE
						+ "=?", new String[] { id, Integer.toString(type) },
				null, null, null);

		if (c.getCount() > 0) {
			c.moveToFirst();

			try {
				Message m = createMessage(c);
				c.close();
				return m;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		c.close();

		return null;
	}

	private Message createMessage(Cursor c) throws MalformedURLException,
			JSONException {
		Message mensagem = new Message();

		mensagem.setIdMensagem(c.getString(0));
		mensagem.setMensagem(c.getString(1));
		mensagem.setNome_usuario(c.getString(2));
		mensagem.setImagePath(new URL(c.getString(3)));
		mensagem.setTipo(c.getInt(4));
		mensagem.setData(new Date(c.getLong(5)));
		mensagem.setIdUser(c.getLong(6));
		mensagem.setAddtions(new JSONObject(c.getString(7)));

		int tipo = mensagem.getTipo();
		if (tipo == Message.TIPO_STATUS || tipo == Message.TIPO_TWEET_SEARCH) {
			mensagem.setAction(OpenTwitterStatusAction.getInstance());
		}
		return mensagem;
	}

	protected Vector<Message> getMensagemOf(int type) {
		try {

			Cursor c = db.getDB().query(DataBase.TB_MESSAGE, null,
					DataBase.MESSAGE_TYPE + "=?",
					new String[] { Integer.toString(type) }, null, null, null);

			Vector<Message> mensagens = new Vector<Message>();
			for (int i = 0; i < c.getCount(); i++) {
				c.moveToPosition(i);

				Message mensagem = createMessage(c);

				mensagens.add(mensagem);

			}
			c.close();
			Collections.sort(mensagens);
			return mensagens;
		} catch (Exception e) {
			return null;
		}

	}

	protected Vector<Message> getMensagemOfLikeId(int type, String idLike) {
		try {

			Cursor c = db.getDB().query(DataBase.TB_MESSAGE, null,
					DataBase.MESSAGE_TYPE + "=?",
					new String[] { Integer.toString(type) }, null, null, null);

			Vector<Message> mensagens = new Vector<Message>();
			for (int i = 0; i < c.getCount(); i++) {
				c.moveToPosition(i);

				Message mensagem = createMessage(c);
				if (mensagem.getIdMensagem().startsWith(idLike))
					mensagens.add(mensagem);

			}

			c.close();
			return mensagens;
		} catch (Exception e) {
			return null;
		}

	}

	protected boolean existsStatus(String id, int tipo) {
		Cursor c = db.getDB().query(
				DataBase.TB_MESSAGE,
				null,
				DataBase.MESSAGE_ID + "=? and " + DataBase.MESSAGE_TYPE
						+ "=?", new String[] { id, Integer.toString(tipo) },
				null, null, null);

		boolean result = c.getCount() > 0;
		c.close();
		return result;
	}

	protected void deleteAll() {
		db.getDB().delete(DataBase.TB_MESSAGE, null, null);
	}

	protected void deleteAll(int type) {
		if (type == Message.TIPO_FACEBOOK_GROUP
				|| type == Message.TIPO_NEWS_FEEDS) {
			Vector<Message> msgs = getMensagemOf(type);
			for (Message m : msgs) {
				deleteAllComents(m.getIdMensagem());
			}

		}

		db.getDB().delete(DataBase.TB_MESSAGE, DataBase.MESSAGE_TYPE + "=?",
				new String[] { Integer.toString(type) });
	}

	protected void deleteAllComents(String id) {
		Vector<Message> msgs = getMensagemOf(Message.TIPO_FACE_COMENTARIO);
		for (Message m : msgs) {
			if (m.getIdMensagem().startsWith(id))
				delete(m.getIdMensagem(), Message.TIPO_FACE_COMENTARIO);
		}
	}

	protected void delete(String id, int type) {
		db.getDB().delete(
				DataBase.TB_MESSAGE,
				DataBase.MESSAGE_ID + "=? and " + DataBase.MESSAGE_TYPE
						+ "=?", new String[] { id, Integer.toString(type) });
		if (type == Message.TIPO_FACEBOOK_GROUP
				|| type == Message.TIPO_NEWS_FEEDS)
			deleteAllComents(id);
	}

	protected void deleteAllSearch() {
		db.getDB().delete(DataBase.TB_MESSAGE, DataBase.MESSAGE_TYPE + "=?",
				new String[] { Integer.toString(Message.TIPO_TWEET_SEARCH) });
	}

	protected void deleteAllTo(long date) {
		db.getDB().delete(DataBase.TB_MESSAGE,
				DataBase.MESSAGE_DATE + "<" + date, null);
	}

	protected int count(int type) {
		Cursor c = db.getDB().query(DataBase.TB_MESSAGE, null,
				DataBase.MESSAGE_TYPE + "=?",
				new String[] { Integer.toString(type) }, null, null, null);

		int out = c.getCount();
		c.close();
		return out;
	}

}
