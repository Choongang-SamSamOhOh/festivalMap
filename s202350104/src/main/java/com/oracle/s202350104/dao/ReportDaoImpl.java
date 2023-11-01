package com.oracle.s202350104.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350104.model.Report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReportDaoImpl implements ReportDao {

	private final SqlSession session;
	
	@Override
	public List<Report> listReport() {
		List<Report> listReport = null;
		try {
			listReport = session.selectList("shReportListAll");
			log.info("listReport.size()->"+listReport.size());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listReport;
	}

	@Override
	public Report detailBoard(int board_id) {
		Report detailBoard = new Report();
		try {
			detailBoard = session.selectOne("shBoardDetail",board_id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return detailBoard;
	}

	@Override
	public List<Report> reportDetail(int board_id) {
		List<Report> reportDetail = null;
		try {
			reportDetail = session.selectList("shReportDetail", board_id);
		} catch (Exception e) {
			
		}
		return reportDetail;
	}

	@Override
	public int updateBoard(int id) {
		int updateBoard = 0;
		try {
			updateBoard = session.update("shBoardUpdate",id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return updateBoard;
	}

	@Override
	public int reportUpdate(int id) {
		int reportUpdate = 0;
		try {
			reportUpdate = session.update("shReportUpdate",id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return reportUpdate;
	}

}
