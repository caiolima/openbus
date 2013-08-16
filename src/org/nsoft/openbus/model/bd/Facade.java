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
import java.util.ArrayList;
import java.util.Vector;

import org.nsoft.openbus.model.Account;
import org.nsoft.openbus.model.CollumnConfig;
import org.nsoft.openbus.model.Draft;
import org.nsoft.openbus.model.LineBus;
import org.nsoft.openbus.model.Message;
import org.nsoft.openbus.model.TwitterAccount;
import org.nsoft.openbus.model.User;
import org.nsoft.openbus.utils.TwitterUtils;

import android.content.Context;


public class Facade {

	private static Facade singleton;
	private TwitterDB twitterBD;
	private MessageDB mensagemBD;
	private CollumnConfigDao configDao;
	private UserDB userBD;
	private DraftDB draftBD;
	private LineDB linhasBD;

	public static Facade getInstance(Context ctx) {
		if (singleton == null)
			singleton = new Facade(ctx);

		return singleton;
	}

	private Facade(Context ctx) {

		twitterBD = new TwitterDB(ctx);
		mensagemBD = new MessageDB(ctx);
		configDao = new CollumnConfigDao(ctx);
		userBD = new UserDB(ctx);
		draftBD = new DraftDB(ctx);
		linhasBD = new LineDB(ctx); 

	}

	public static void destroy() {
		singleton = null;
	}

	public int insert(TwitterAccount u) {

		return twitterBD.insert(u);
	}

	public void logoutTwitter() {
		twitterBD.delete();
		mensagemBD.deleteAll(Message.TIPO_STATUS);
		mensagemBD.deleteAll(Message.TIPO_TWEET_SEARCH);
		TwitterUtils.logoutTwitter();
	}

	public Vector<Account> lastSavedSession() {
		Vector<Account> retorno = new Vector<Account>();
		retorno.addAll(twitterBD.lastSavedSession());
		
		return retorno;
	}

	

	public boolean insert(final Message m) {
		if (mensagemBD.existsStatus(m.getIdMensagem(), m.getTipo()))
			return false;

		mensagemBD.insert(m);

		return true;
	}

	public Vector<Message> getAllMensagens() {
		try {
			return mensagemBD.getAllMensagens();
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public boolean exsistsStatus(String id, int tipo) {
		return mensagemBD.existsStatus(id, tipo);
	}

	public void deletAllMensagem(int type) {
		mensagemBD.deleteAll(type);
	}

	public void deletAllMensagem() {
		mensagemBD.deleteAll();
	}

	public void deleteAllTo(long date) {
		mensagemBD.deleteAllTo(date);
	}

	public int getCountMensagem(int type) {
		return mensagemBD.count(type);
	}

	public Vector<Message> getMensagemOf(int type) {
		return mensagemBD.getMensagemOf(type);
	}

	public void deleteMensagem(final String id, final int type) {
		mensagemBD.delete(id, type);
	}

	public Message getOneMessage(String id, int type) {
		try {
			return mensagemBD.getOne(id, type);
		} catch (Exception e) {
			return null;
		}
	}

	public int insert(User u) {
		int return_value = -1;

		User user = userBD.getOne(u.getId(), u.getType());

		if (user != null)
			return_value = user.getSysID();
		else
			return_value = userBD.insert(u);

		return return_value;
	}

	public User getOneUser(long id, int type) {
		try {
			return userBD.getOne(id, type);
		} catch (Exception e) {
			return null;
		}

	}

	public User getOneUser(int id) {
		try {
			return userBD.getOne(id);
		} catch (Exception e) {
			return null;
		}
	}

	

	public void update(Message m) {
		mensagemBD.update(m);
	}



	public Vector<Message> getMensagemOfLikeId(int type, String idLike) {
		return mensagemBD.getMensagemOfLikeId(type, idLike);
	}

	public void insert(Draft d) {
		draftBD.insert(d);
	}

	public void deleteDraft(String id) {
		draftBD.delete(id);
	}

	public Draft getOneDraft(String id) {
		return draftBD.getOne(id);
	}

	public boolean existsDraft(String id) {
		return draftBD.exists(id);
	}
	
	public void insert(CollumnConfig c) {
		if (configDao.existsCollumnType(c.getType()))
			return;

		configDao.insert(c);
	}

	public void deleteCollum(int pos) {
		configDao.delete(pos);

	}

	public ArrayList<CollumnConfig> getAllConfig() {

		return configDao.getAll();

	}

	public CollumnConfig getOneConfig(int pos) {
		return configDao.getOne(pos);
	}

	public void update(CollumnConfig config) {
		configDao.update(config);
	}

	public void changePos(CollumnConfig config, int toPos) {
		configDao.changePos(config, toPos);
	}

	public void deleteAllCollumnConfig() {
		configDao.deleteAll();
	}

	public boolean existsCollumnType(String type) {
		return configDao.existsCollumnType(type);
	}

	public Vector<LineBus> getAllLinhas(){
		return linhasBD.getAll();
	}
	
}
