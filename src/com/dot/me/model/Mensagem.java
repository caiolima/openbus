package com.dot.me.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.DirectMessage;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

import android.content.Context;

import com.dot.me.command.IMessageAction;
import com.dot.me.command.OpenLinkActivity;
import com.dot.me.command.OpenTwitterStatusAction;
import com.dot.me.utils.PictureInfo;
 import com.dot.me.utils.TwitterUtils;

public class Mensagem implements Comparable<Mensagem> {

	private String idMensagem;
	private String mensagem;
	private String nome_usuario;
	private Date data;
	private URL imagePath;
	private int tipo;
	private long idUser;
	private JSONObject addtions;
	private IMessageAction action;

	public static final int TIPO_STATUS = 0, TIPO_TWEET_SEARCH = 1,
			TIPO_NEWS_FEEDS = 2, TIPO_FACE_COMENTARIO = 3,
			TIPO_FACEBOOK_GROUP = 4, TIPO_FACEBOOK_NOTIFICATION = 5;

	public String getIdMensagem() {
		return idMensagem;
	}

	public void setIdMensagem(String idMensagem) {
		this.idMensagem = idMensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getNome_usuario() {
		return nome_usuario;
	}

	public void setNome_usuario(String nome_usuario) {
		this.nome_usuario = nome_usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public URL getImagePath() {
		return imagePath;
	}

	public void setImagePath(URL imagePath) {
		this.imagePath = imagePath;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public static Mensagem createFromDirectMensagem(DirectMessage dm) {
		try {
			Mensagem mensagem = new Mensagem();

			mensagem.setAction(OpenTwitterStatusAction.getInstance());

			mensagem.idMensagem = Long.toString(dm.getId());
			mensagem.nome_usuario = dm.getSender().getName();
			mensagem.mensagem = dm.getText();
//			mensagem.imagePath = dm.getSender().getProfileImageURL();
			mensagem.data = dm.getCreatedAt();
			mensagem.idUser = dm.getSender().getId();
			mensagem.tipo = TIPO_STATUS;

			return mensagem;
		} catch (Exception e) {
			return null;
		}
	}

	public static Mensagem creteFromTwitterStatus(Status s) {
		try {
			Mensagem mensagem = new Mensagem();

			String text = s.getText();

			for (HashtagEntity h : s.getHashtagEntities()) {

				String subText = "<a href=\"twitter_search://do_search?search="
						+ h.getText() + "\">#" + h.getText() + "</a>";
				text = text.replace("#" + h.getText(), subText);

			}

			for (UserMentionEntity u : s.getUserMentionEntities()) {

				String subText = "<a href=\"twitter_search_user://find_user?username="
						+ u.getScreenName()
						+ "\">@"
						+ u.getScreenName()
						+ "</a>";
				text = text.replace("@" + u.getScreenName(), subText);

			}

			mensagem.setAction(OpenTwitterStatusAction.getInstance());
			mensagem.addtions = createAddtions(s);
			mensagem.addtions.put("htmlText", text);
			mensagem.idMensagem = Long.toString(s.getId());
			mensagem.nome_usuario = s.getUser().getName();
			mensagem.mensagem = s.getText();
			mensagem.imagePath = new URL(s.getUser().getOriginalProfileImageURL());
			mensagem.data = s.getCreatedAt();
			mensagem.idUser = s.getUser().getId();
			mensagem.tipo = TIPO_STATUS;

			return mensagem;
		} catch (JSONException e) {
			return null;
		} catch (MalformedURLException e) {
			return null;
		}
	}

	private static JSONObject createAddtions(Status s) throws JSONException {
		JSONObject json = new JSONObject();

		Vector<String> metions = new Vector<String>();
		UserMentionEntity[] in_metions = s.getUserMentionEntities();
		if (in_metions != null) {
			for (UserMentionEntity metion : in_metions) {
				metions.add(metion.getName());
			}
		}

		URLEntity[] urls = s.getURLEntities();

		Vector<String> image_files = new Vector<String>();
		MediaEntity[] in_medias = s.getMediaEntities();
		if (in_medias != null) {
			for (MediaEntity media : in_medias) {
				image_files.add(media.getMediaURL().toString());
			}
		}

		json.put("metions", metions);
		json.put("image_files", metions);

		json.put("inReplyId", s.getInReplyToStatusId());
		return json;

	}	
	
	@Override
	public int compareTo(Mensagem another) {
		return another.getUpdatedTime().compareTo(getUpdatedTime());
	}

	@Override
	public boolean equals(Object o) {
		try {
			Mensagem other = (Mensagem) o;
			return other.getIdMensagem().equals(idMensagem)
					&& tipo == other.tipo;
		} catch (ClassCastException e) {
			return false;
		}
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public JSONObject getAddtions() {
		return addtions;
	}

	public void setAddtions(JSONObject addtions) {
		this.addtions = addtions;
	}

	public long getInReplyId() {
		try {
			long out = addtions.getLong("inReplyId");
			return out;
		} catch (JSONException e) {
			return -1;
		}

	}

	public int getLikesCount() {
		JSONObject additions = this.addtions;

		try {
			JSONObject likesJSON = addtions.getJSONObject("likes");
			return likesJSON.getInt("count");

		} catch (JSONException e) {
			return 0;
		} catch (NullPointerException e) {
			return 0;
		}

	}

	public String getTypeMessage() {
		try {
			return addtions.getString("type");
		} catch (JSONException e) {
			return null;
		}
	}

	public PictureInfo getPictureUrl() {
		String type = getTypeMessage();
		if (type != null) {
			if (type.equals("photo")) {
				String pictureAdress = null;
				try {
					pictureAdress = addtions.getString("picture");
					PictureInfo pInfo = null;
					if (pictureAdress.equals("")) {
						pInfo = new PictureInfo(
								addtions.getJSONObject("pic_info"));
					}

					if (pInfo == null) {
						pInfo = new PictureInfo();
						pInfo.setSURL(new URL(pictureAdress.replace("_s", "_a")));
						pInfo.setNormalURL(new URL(pictureAdress.replace("_s",
								"_n")));
					}
					return pInfo;
				} catch (JSONException e) {

				} catch (MalformedURLException e) {

				}
			}
		}
		return null;
	}

	public int getCommentsCount() {
		try {
			JSONObject commentsJSON = addtions.getJSONObject("comments");
			return commentsJSON.getInt("count");
		} catch (JSONException e) {
			return 0;
		} catch (NullPointerException e) {
			return 0;
		}

	}

	public String[] getAllComments() {
		try {
			JSONObject commentsJSON = addtions.getJSONObject("comments");
			JSONArray array = commentsJSON.getJSONArray("data");
			String[] comments = new String[array.length()];
			for (int i = 0; i < comments.length; i++) {
				comments[i] = array.getJSONObject(i).getString("id");
			}
			return comments;
		} catch (JSONException e) {
			return new String[0];
		}
	}

	public Date getUpdatedTime() {
		try {
			String data = addtions.getString("updated_time");
			return TwitterUtils.getTime(data);
		} catch (Exception e) {
			return data;
		}

	}

	public IMessageAction getAction() {
		return action;
	}

	public void setAction(IMessageAction action) {
		this.action = action;
	}

	public boolean isLiked() {
		try {
			return addtions.getBoolean("liked");
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isValidToFilter() {
		int tipo = getTipo();
		if (tipo == TIPO_FACEBOOK_GROUP || tipo == TIPO_NEWS_FEEDS
				|| tipo == TIPO_STATUS)
			return true;

		return false;
	}

	public String getHtmlText() {
		try {
			return addtions.getString("htmlText");
		} catch (JSONException e) {
			return mensagem;
		}
	}

	public String getAnalyzableText() {

		String out = "";
		out += mensagem;
		JSONObject link = null;
		try {
			link = new JSONObject(addtions.getString("link_details"));
			out += " " + link.getString("name");
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (link != null) {
			try {
				out += " " + link.getString("caption");
			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				out += " " + link.getString("description");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return out;
	}

}
