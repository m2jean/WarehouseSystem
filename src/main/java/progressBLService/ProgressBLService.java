package progressBLService;

import java.util.ArrayList;

import enums.ResultMessage;
import VO.CheckInVO;
import VO.CheckOutVO;
import VO.CircumstanceVO;
import VO.CostVO;
import VO.ExportVO;
import VO.ImportVO;
import VO.OverflowVO;
import VO.PresentVO;
import VO.ProgressVO;
import VO.ReturnVO;
import VO.SaleVO;

public interface ProgressBLService {
	public ProgressVO getBill(String stime,String etime,String kind,String coutomer,String yewu,String cangku);
	public SaleVO hong(SaleVO vo);
	public PresentVO hong(PresentVO present);
	public ReturnVO hong(ReturnVO vo);
	public ImportVO hong(ImportVO vo);
	public ExportVO hong(ExportVO vo);
	public CheckInVO hong(CheckInVO vo);
	public CheckOutVO hong(CheckOutVO vo);
	public CostVO hong(CostVO vo);
	public ResultMessage hongcopy(SaleVO vo,PresentVO present);
	public ResultMessage hongcopy(ReturnVO vo);
	public ResultMessage hongcopy(ImportVO vo);
	public ResultMessage hongcopy(ExportVO vo);
	public ResultMessage hongcopy(CheckInVO vo);
	public ResultMessage hongcopy(CheckOutVO vo);
	public ResultMessage hongcopy(CostVO vo);
	public ResultMessage hongcopy(PresentVO vo);
	public CircumstanceVO getCircumstance(String stime,String etime);
	public ResultMessage ImportToExcel(ArrayList<ImportVO> vo,String path);
	public ResultMessage ExportToExcel(ArrayList<ExportVO> vo,String path);
	public ResultMessage SaleToExcel(ArrayList<SaleVO> vo,String path);
	public ResultMessage ReturnToExcel(ArrayList<ReturnVO> vo,String path);
	public ResultMessage CheckInToExcel(ArrayList<CheckInVO> vo,String path);
	public ResultMessage CheckOutToExcel(ArrayList<CheckOutVO> vo,String path);
	public ResultMessage CostToExcel(ArrayList<CostVO> vo,String path);
	public ResultMessage OverflowToExcel(ArrayList<OverflowVO> vo,String path);
	public ResultMessage PresentToExcel(ArrayList<PresentVO> vo,String path);
}
