package com.portal.integration.jira;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntegrationFieldsJIRA {
	// Marca
	private String customfield_11069;
	// Modelo
	private String customfield_11070;
	// Ano Modelo
	private String customfield_11071;
	// Placa
	private String customfield_10253;
	// Chassi
	private String customfield_10257;
	// DATA DA COMPRA
	private Date customfield_11072;
	// Marca / Modelo
	private String customfield_10032;
	// Numero OS
	private String customfield_10256;
	// Lista de locais a ser executado serviço especial
	private ArrayList<String> customfield_11102 = new ArrayList<String>();

	private ArrayList<IntegrationSubtaskOrParentJIRA> subtasks = new ArrayList<IntegrationSubtaskOrParentJIRA>();
	private IntegrationIssuetypeJIRA issuetype;
	private Status status;
	private IntegrationSubtaskOrParentJIRA parent;
	private Date created;
	private Date updated;
	private Date statuscategorychangedate;

	// Olhar
	private Resolution resolution;
	private Description description;
	private ArrayList<Object> attachment = new ArrayList<Object>();
	private IntegrationCommentJIRA comment;

	/* Gets e Sets com nomes Indicativos */
	public String getMarca() {
		return customfield_11069;
	}

	public String getModelo() {
		return customfield_11070;
	}

	public String getAnoModelo() {
		return customfield_11071;
	}

	public String getPlaca() {
		return customfield_10253;
	}

	public String getChassi() {
		return customfield_10257;
	}

	public Date getDataCompra() {
		return customfield_11072;
	}

	public String getMarcaModelo() {
		return customfield_10032;
	}

	public String getOS() {
		return customfield_10256;
	}

	public ArrayList<String> getLocaisServicoEspeciais() {
		return customfield_11102;
	}

	public void setLocaisServicoEspeciais(ArrayList<String> array) {
		this.customfield_11102 = array;
	}

	/*------------------------------------------*/

	public String getCustomfield_11069() {
		return customfield_11069;
	}

	public void setCustomfield_11069(String customfield_11069) {
		this.customfield_11069 = customfield_11069;
	}

	public String getCustomfield_11070() {
		return customfield_11070;
	}

	public void setCustomfield_11070(String customfield_11070) {
		this.customfield_11070 = customfield_11070;
	}

	public String getCustomfield_11071() {
		return customfield_11071;
	}

	public void setCustomfield_11071(String customfield_11071) {
		this.customfield_11071 = customfield_11071;
	}

	public String getCustomfield_10253() {
		return customfield_10253;
	}

	public void setCustomfield_10253(String customfield_10253) {
		this.customfield_10253 = customfield_10253;
	}

	public String getCustomfield_10257() {
		return customfield_10257;
	}

	public void setCustomfield_10257(String customfield_10257) {
		this.customfield_10257 = customfield_10257;
	}

	public Date getCustomfield_11072() {
		return customfield_11072;
	}

	public void setCustomfield_11072(Date customfield_11072) {
		this.customfield_11072 = customfield_11072;
	}

	public String getCustomfield_10032() {
		return customfield_10032;
	}

	public void setCustomfield_10032(String customfield_10032) {
		this.customfield_10032 = customfield_10032;
	}

	public String getCustomfield_10256() {
		return customfield_10256;
	}

	public void setCustomfield_10256(String customfield_10256) {
		this.customfield_10256 = customfield_10256;
	}

	public ArrayList<IntegrationSubtaskOrParentJIRA> getSubtasks() {
		return subtasks;
	}

	public void setSubtasks(ArrayList<IntegrationSubtaskOrParentJIRA> subtasks) {
		this.subtasks = subtasks;
	}

	public IntegrationIssuetypeJIRA getIssuetype() {
		return issuetype;
	}

	public void setIssuetype(IntegrationIssuetypeJIRA issuetype) {
		this.issuetype = issuetype;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public IntegrationSubtaskOrParentJIRA getParent() {
		return parent;
	}

	public void setParent(IntegrationSubtaskOrParentJIRA parent) {
		this.parent = parent;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getStatuscategorychangedate() {
		return statuscategorychangedate;
	}

	public void setStatuscategorychangedate(Date statuscategorychangedate) {
		this.statuscategorychangedate = statuscategorychangedate;
	}

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public ArrayList<Object> getAttachment() {
		return attachment;
	}

	public void setAttachment(ArrayList<Object> attachment) {
		this.attachment = attachment;
	}

	public IntegrationCommentJIRA getComment() {
		return comment;
	}

	public void setComment(IntegrationCommentJIRA comment) {
		this.comment = comment;
	}

}
