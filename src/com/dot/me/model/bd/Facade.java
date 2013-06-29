package com.dot.me.model.bd;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;

import com.dot.me.model.Account;
import com.dot.me.model.CollumnConfig;
import com.dot.me.model.Draft;
import com.dot.me.model.Mensagem;
import com.dot.me.model.TwitterAccount;
import com.dot.me.model.User;
import com.dot.me.utils.TwitterUtils;

public class Facade {

	private static Facade singleton;
	private TwitterBD twitterBD;
	private MensagemBD mensagemBD;
	private TwitterSearchBD twitterSearchBD;
	private CollumnConfigDao configDao;
	private UserDB userBD;
	private DraftBD draftBD;

	public static Facade getInstance(Context ctx) {
		if (singleton == null)
			singleton = new Facade(ctx);

		return singleton;
	}

	private Facade(Context ctx) {

		twitterBD = new TwitterBD(ctx);
		mensagemBD = new MensagemBD(ctx);
		twitterSearchBD = new TwitterSearchBD(ctx);
		configDao = new CollumnConfigDao(ctx);
		userBD = new UserDB(ctx);
		draftBD = new DraftBD(ctx);

	}

	public static void destroy() {
		singleton = null;
	}

	public int insert(TwitterAccount u) {

		return twitterBD.insert(u);
	}

	public void logoutTwitter() {
		twitterBD.delete();
		mensagemBD.deleteAll(Mensagem.TIPO_STATUS);
		mensagemBD.deleteAll(Mensagem.TIPO_TWEET_SEARCH);
		twitterSearchBD.deleteAll();
		TwitterUtils.logoutTwitter();
	}

	public Vector<Account> lastSavedSession() {
		Vector<Account> retorno = new Vector<Account>();
		retorno.addAll(twitterBD.lastSavedSession());
		
		return retorno;
	}

	

	public boolean insert(final Mensagem m) {
		if (mensagemBD.existsStatus(m.getIdMensagem(), m.getTipo()))
			return false;

		mensagemBD.insert(m);

		return true;
	}

	public Vector<Mensagem> getAllMensagens() {
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

	public Vector<Mensagem> getMensagemOf(int type) {
		return mensagemBD.getMensagemOf(type);
	}

	public int insert(String search) {

		return twitterSearchBD.insert(search.toLowerCase().trim());
	}

	public void deleteSearch(String search) {
		twitterSearchBD.delete(search);
	}

	public void deleteAllSearch() {
		mensagemBD.deleteAllSearch();
	}

	public Vector<String> getAllSearches() {
		return twitterSearchBD.getAll();
	}

	public boolean wasSearchAdded(String search) {
		return twitterSearchBD.wasAdded(search);
	}

	public void deleteMensagem(final String id, final int type) {
		mensagemBD.delete(id, type);
	}

	public Mensagem getOneMessage(String id, int type) {
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

	

	public void update(Mensagem m) {
		mensagemBD.update(m);
	}



	public Vector<Mensagem> getMensagemOfLikeId(int type, String idLike) {
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
}
