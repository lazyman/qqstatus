package cn.com.lazyhome.qqstatus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import cn.com.lazyhome.qqstatus.bean.Log;
import cn.com.lazyhome.qqstatus.util.HibernateUtil;
import cn.com.lazyhome.qqstatus.util.Init;

public class LineChart {
	public static int WIDTH = 200;
	public static int HEIGHT = 3000;
	private String qqid = "84074663";
	private Calendar begintime;
	private Calendar inputCalendar;
	private int days;

	public LineChart() {
		begintime = Calendar.getInstance();
		days = 1;
	}
	public LineChart(String qqid) {
		this();
		this.qqid = qqid;
	}

	public String writeImage() throws Exception {
		final CategoryDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);
		SimpleDateFormat sdf = new SimpleDateFormat("-yyyy-MM-dd");
		
		if(dataset.getRowCount() == 0) {
			throw new Exception("no-data");
		}
		try {
			String root = Init.getRootpath();
			if(root == null) {
				root = "d:/";
			} else {
				root +=  "statimg/";
			}
			File rootf = new File(root);
			rootf.mkdirs();
			File file = new File(rootf, qqid + sdf.format(inputCalendar.getTime()) + ".png");
			
			Calendar yestoday = new GregorianCalendar();
			yestoday.add(Calendar.DATE, -1);
			if(yestoday.before(begintime) || !file.exists()) {
				ChartUtilities.saveChartAsPNG(file, chart, WIDTH, HEIGHT);
			}
			
			return file.getPath();
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public String getQqid() {
		return qqid;
	}
	public void setQqid(String qqid) {
		this.qqid = qqid;
	}
	public Calendar getBegintime() {
		return begintime;
	}
	public void setBegintime(Calendar begintime) {
		this.begintime = begintime;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	/**
	 * Creates a sample dataset.
	 * 
	 * @return a sample dataset.
	 */
	private CategoryDataset createDataset() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");

		Session s = HibernateUtil.getSessionFactory().openSession();
		String hql = "from Log l where l.qqId = ? and l.time<? and l.time>? order by l.time";
		Query q = s.createQuery(hql);
		q.setString(0, qqid);
		
		inputCalendar = Calendar.getInstance();
		inputCalendar.setTimeInMillis(begintime.getTimeInMillis());
		begintime.add(Calendar.DATE, 1);
		q.setCalendar(1, begintime);

		Calendar yestoday = new GregorianCalendar();
		yestoday.setTimeInMillis(begintime.getTimeInMillis());
		yestoday.add(Calendar.DATE, -days);
		q.setCalendar(2, yestoday);

		List<Log> logs = q.list();

		// create the dataset...
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		int size = logs.size();
		for (int i = 0; i < size; i++) {
			Log log = logs.get(i);
			dataset.addValue(log.getStatus(), qqid, sdf.format(log.getTime()));
		}
		s.close();

		return dataset;

	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return a chart.
	 */
	private JFreeChart createChart(final CategoryDataset dataset) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

		final JFreeChart chart = ChartFactory.createLineChart(
				sdf.format(inputCalendar.getTime()) + " QQ 在线图示", // chart title 标题
				"时间", // domain axis label Y轴
				"在线状态", // range axis label X轴
				dataset, // data
				PlotOrientation.HORIZONTAL, // orientation
				true, // include legend 图例
				true, // tooltips
				false // urls
				);

		// final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);

		final Shape[] shapes = new Shape[3];
		int[] xpoints;
		int[] ypoints;

		// right-pointing triangle
		xpoints = new int[] { -3, 3, -3 };
		ypoints = new int[] { -3, 0, 3 };
		shapes[0] = new Polygon(xpoints, ypoints, 3);

		// vertical rectangle
		shapes[1] = new Rectangle2D.Double(-2, -3, 3, 6);

		// left-pointing triangle
		xpoints = new int[] { -3, 3, 3 };
		ypoints = new int[] { 0, -3, 3 };
		shapes[2] = new Polygon(xpoints, ypoints, 3);

		final DrawingSupplier supplier = new DefaultDrawingSupplier(
				DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, shapes);
		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setDrawingSupplier(supplier);

		chart.setBackgroundPaint(Color.orange);

		// set the stroke for each series...
//		plot.getRenderer().setSeriesStroke(
//				0,
//				new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
//						BasicStroke.JOIN_ROUND, 1.0f,
//						new float[] { 10.0f, 6.0f }, 0.0f));
//		plot.getRenderer().setSeriesStroke(
//				1,
//				new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
//						BasicStroke.JOIN_ROUND, 1.0f,
//						new float[] { 6.0f, 6.0f }, 0.0f));
//		plot.getRenderer().setSeriesStroke(
//				2,
//				new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
//						BasicStroke.JOIN_ROUND, 1.0f,
//						new float[] { 2.0f, 6.0f }, 0.0f));

		// customise the renderer...
		// final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
		// .getRenderer();
		// renderer.setDrawShapes(true);
		// renderer.setItemLabelsVisible(true);
		// renderer.setLabelGenerator(new StandardCategoryLabelGenerator());

		// customise the range axis...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(false);
		rangeAxis.setUpperMargin(0.12);
		// CategoryPlot plot = chart.getCategoryPlot();
		// 设置横轴的字体
		CategoryAxis categoryAxis = plot.getDomainAxis();
		categoryAxis.setLabelFont(new Font("宋体", Font.BOLD, 22));// x轴标题字体
		categoryAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));// x轴刻度字体

		
		return chart;

	}

	public static void main(String[] args) {
		LineChart line = new LineChart();
		try {
			line.writeImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
