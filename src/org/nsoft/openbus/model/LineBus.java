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
 * Date: 02 - 07 - 2013
 */

package org.nsoft.openbus.model;

public class LineBus {

	private int idLinha;
	private String descricao;
	private String hash;

	public int getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(int idLinha) {
		this.idLinha = idLinha;
	}

	public String getDescicao() {
		return descricao;
	}

	public void setDescicao(String descicao) {
		this.descricao = descicao;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
	

}
