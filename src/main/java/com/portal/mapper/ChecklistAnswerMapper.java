package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.model.ChecklistAnswerModel;
import com.portal.model.ChecklistQuestionModel;
import com.portal.model.ClassifierModel;
import com.portal.model.TaskModel;
import com.portal.utils.PortalNumberUtils;

public class ChecklistAnswerMapper implements ResultSetExtractor<List<ChecklistAnswerModel>> {

	@Override
	public List<ChecklistAnswerModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<ChecklistAnswerModel> list = new LinkedList<ChecklistAnswerModel>();

		if (rs != null) {
			while (rs.next()) {

				ChecklistAnswerModel model = new ChecklistAnswerModel();

				model.setId(rs.getInt("cqa_id"));
				model.setCreationDate(rs.getDate("creation_date"));

				if (rs.getInt("qas_id") > 0) {
					ChecklistQuestionModel question = new ChecklistQuestionModel();
					question.setId(rs.getInt("qst_qas_id"));
					question.setQuestion(rs.getString("qst_question"));
					question.setCreationDate(rs.getDate("qst_creation_date"));
					question.setActive(PortalNumberUtils.intToBoolean(rs.getInt("qst_active")));
					question.setCklId(rs.getInt("qst_ckg_id"));

					model.setQuestion(question);
				}

				model.setComment(rs.getString("comment"));
				model.setTask(new TaskModel(rs.getInt("tsk_id")));
				model.setResponsibleForAnswer(rs.getString("responsible_for_answer"));

				ClassifierModel answer = new ClassifierModel();
				answer.setId(rs.getInt("cla_id"));
				answer.setValue(rs.getString("cla_value"));
				answer.setType(rs.getString("cla_type"));
				model.setAnswer(answer);

				list.add(model);
			}
		}

		return list;
	}
}
