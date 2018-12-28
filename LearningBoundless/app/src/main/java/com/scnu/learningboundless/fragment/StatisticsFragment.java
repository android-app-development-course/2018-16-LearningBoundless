package com.scnu.learningboundless.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.scnu.learningboundless.R;
import com.scnu.learningboundless.base.BaseFragment;

import org.w3c.dom.Entity;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by WuchangI on 2018/12/14.
 */

public class StatisticsFragment extends Fragment {

    // 绑定UI控件，下面不用再findViewById
//    @BindView(R.id.text_sean)

    protected View mRootView;

    TextView textView;

    // 因为BindView可能对一些第三方控件不兼容，所以改用findViewById
//    @BindView(R.id.mPieChart)
    PieChart mPieChart;

    //    @BindView(R.id.mLineChart)
    LineChart mLineChart;

    //    @BindView(R.id.mBarChart)
    BarChart mBarChart;

    private final int initLineChartX = 1;  // 折线图点开始横坐标
    private final int FINISH_COLOR = ColorTemplate.JOYFUL_COLORS[3];
    private final int UNFINISH_COLOR = ColorTemplate.JOYFUL_COLORS[2];

    /**
     * 得到当前Fragment对应的布局文件的id
     *
     * @return
     */
    protected int getContentLayoutId() {
        return R.layout.fragment_statistics;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        if(mRootView == null){
            int layoutId = getContentLayoutId();
            View rootView = inflater.inflate(layoutId, container, false);

            initWidget(rootView);

            mRootView = rootView;
        } else {
            if(mRootView.getParent() != null){
                ((ViewGroup)(mRootView.getParent())).removeView(mRootView);
            }
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
    }

    protected void initWidget(View rootView) {

        mPieChart = rootView.findViewById(R.id.mPieChart);
        mLineChart = rootView.findViewById(R.id.mLineChart);
        mBarChart = rootView.findViewById(R.id.mBarChart);

        initPieChartWidget();
        initLineChartWidget();
        initBarChartWidget();
    }

    protected void initData() {
        setTestData();
    }


    /**
     * 初始化柱状图UI控件
     */
    private void initBarChartWidget(){
        mBarChart.setNoDataText("暂时没有数据哦！");
        mBarChart.setDrawBarShadow(false); // 背景阴影
        mBarChart.setHighlightFullBarEnabled(false);
        mBarChart.setDrawBorders(false); // 显示边框

        Description description = new Description();
        description.setEnabled(false);
        mBarChart.setDescription(description);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mBarChart.getAxisLeft();
        YAxis rightAxis = mBarChart.getAxisRight();

        leftAxis.setEnabled(false);  // 不显示左侧Y轴


        xAxis.setDrawGridLines(false); //不显示X轴网格线
        rightAxis.enableGridDashedLine(10f, 10f, 0f); //右侧Y轴网格线设置为虚线


        //保证Y轴从0开始，不然会上移一点
        xAxis.setAxisMinimum(0f);
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);

        Legend legend = mBarChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);


    }

    /**
     * 初始化折线图UI控件
     */
    private void initLineChartWidget(){
        // 初始化折线图
        Description description = new Description();
//        description.setText("学习轨迹图"); // 标题
//        description.setTextColor(Color.BLACK);
//        description.setTextSize(16);
        description.setEnabled(false);

        mLineChart.setDescription(description);
        mLineChart.setNoDataText("暂时没有数据哦！");
        mLineChart.setNoDataTextColor(Color.BLUE);
        mLineChart.setDrawGridBackground(false); // chart 绘图区后面的背景矩形将绘制
        mLineChart.setDrawBorders(false); // 禁止绘制图表边框的线

        mLineChart.setTouchEnabled(true); // 设置是否可以触摸
        mLineChart.setDragEnabled(true);// 是否可以拖拽
        mLineChart.setScaleEnabled(false);// 是否可以缩放 x和y轴, 默认是true
        mLineChart.setScaleXEnabled(false); // 是否可以缩放 仅x轴
        mLineChart.setScaleYEnabled(true); // 是否可以缩放 仅y轴
        mLineChart.setPinchZoom(true);  // 设置x轴和y轴能否同时缩放。默认是否
        mLineChart.setDoubleTapToZoomEnabled(true);// 设置是否可以通过双击屏幕放大图表。默认是true
        mLineChart.setHighlightPerDragEnabled(true);// 能否拖拽高亮线(数据点与坐标的提示线)，默认是true
        mLineChart.setDragDecelerationEnabled(true);// 拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        mLineChart.setDragDecelerationFrictionCoef(0.99f);// 与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。


        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setEnabled(false); // 设置图表右边的y轴禁用

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "第".concat(String.valueOf((int) value)).concat("天");
            }
        });
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
    }

    /**
     * 初始化总体统计饼状图，设置ui控件显示风格
     */
    private void initPieChartWidget(){

        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend legend = mPieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);
    }


    /**
     * 设置饼状图数据
     * @param nfinish 已经完成的任务数量
     * @param nUnfinish 未完成的任务数量
     */
    private void setPieChartData(int nfinish, int nUnfinish) {

        if(mPieChart.getData() != null) return;  // 一般不会发生

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(nfinish, "已完成"));
        entries.add(new PieEntry(nUnfinish, "未完成"));

        PieDataSet pieDataSet = new PieDataSet(entries, "任务完成情况");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        pieDataSet.setColors(colors);

        PieData data = new PieData(pieDataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }


    /**
     * 设置折线图数据
     * @param nFinished     各个日期完成任务数量列表
     * @param nUnfinished   各个日期未完成任务数量列表
     */
    private void setLineChartData(ArrayList<Integer> nFinished, ArrayList<Integer> nUnfinished){
        ArrayList<Entry> finishedList = new ArrayList<>();
        ArrayList<Entry> unfinishedList = new ArrayList<>();


        // 添加已完成任务数据
        int currentX = initLineChartX;
        for(Integer d: nFinished){
            finishedList.add(new Entry(currentX, d.intValue()));
            currentX += 1;
        }

        // 添加未完成任务数据
        currentX = initLineChartX;
        for(Integer d: nUnfinished){
            unfinishedList.add(new Entry(currentX, d.intValue()));
            currentX += 1;
        }

        // LineDataSet每一个对象就是一条连接线
        LineDataSet set1;
        LineDataSet set2;

        if(mLineChart.getData() != null && mLineChart.getData().getDataSetCount() > 0){
            //获取数据1
            set1 = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
            set1.setValues(finishedList);
            set2 = (LineDataSet) mLineChart.getData().getDataSetByIndex(1);
            set2.setValues(unfinishedList);
            //刷新数据
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            //设置数据1  参数1：数据源 参数2：图例名称
            set1 = new LineDataSet(finishedList, "已完成数量");
            set1.setColor(FINISH_COLOR);
            set1.setCircleColor(UNFINISH_COLOR);
            set1.setLineWidth(1f);//设置线宽
            set1.setCircleRadius(3f);//设置焦点圆心的大小
            set1.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
            set1.setHighlightLineWidth(2f);//设置点击交点后显示高亮线宽
            set1.setHighlightEnabled(true);//是否禁用点击高亮线
            set1.setHighLightColor(Color.RED);//设置点击交点后显示交高亮线的颜色
            set1.setValueTextSize(9f);//设置显示值的文字大小
            set1.setDrawFilled(false);//设置禁用范围背景填充

            //格式化显示数据
            final DecimalFormat mFormat = new DecimalFormat("###,###,##0");
            IValueFormatter valueFormatter = new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return mFormat.format(value);
                }
            };
            set1.setValueFormatter(valueFormatter);

//            if (Utils.getSDKInt() >= 18) {
//                // fill drawable only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//                set1.setFillDrawable(drawable);//设置范围背景填充
//            } else {
//                set1.setFillColor(Color.BLACK);
//            }

            set1.setFillColor(Color.BLACK);

            //设置数据2
            set2 = new LineDataSet(unfinishedList, "未完成数量");
            set2.setColor(Color.YELLOW);
            set2.setCircleColor(Color.YELLOW);
            set2.setLineWidth(1f);
            set2.setCircleRadius(3f);
            set2.setValueTextSize(10f);
            set2.setValueFormatter(valueFormatter);


            //保存LineDataSet集合
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the datasets
            dataSets.add(set2);
            //创建LineData对象 属于LineChart折线图的数据集合
            LineData data = new LineData(dataSets);
            // 添加到图表中
            mLineChart.setData(data);
            //绘制图表
            mLineChart.invalidate();
        }
    }

    /**
     * 设置柱状图数据
     * @param nFinished 完成任务数量
     * @param nUnfinished 未完成任务数量
     * @param xValues X轴的值，即日期
     */
    private void setBarChartData(ArrayList<Integer> nFinished, ArrayList<Integer> nUnfinished, ArrayList<String> xValues){
        ArrayList<BarEntry> finishedList = new ArrayList<>();
        ArrayList<BarEntry> unfinishedList = new ArrayList<>();

        for(int i = 0; i < nFinished.size(); i++) {
            finishedList.add(new BarEntry(i, nFinished.get(i).intValue()));
            unfinishedList.add(new BarEntry(i, nUnfinished.get(i).intValue()));
        }

        BarDataSet barDataSet1;
        BarDataSet barDataSet2;

        if(mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0){
            barDataSet1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            barDataSet2 = (BarDataSet) mBarChart.getData().getDataSetByIndex(1);
            barDataSet1.setValues(finishedList);
            barDataSet2.setValues(unfinishedList);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        }else{

            barDataSet1 = new BarDataSet(finishedList, "已完成任务数量");
            barDataSet2 = new BarDataSet(unfinishedList, "未完成任务数量");
            List<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);
            dataSets.add(barDataSet2);

            barDataSet1.setColor(FINISH_COLOR);
            barDataSet2.setColor(UNFINISH_COLOR);

            barDataSet1.setDrawValues(true);
            barDataSet2.setDrawValues(true);

            final DecimalFormat mFormat = new DecimalFormat("###,###,##0");
            IValueFormatter valueFormatter = new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return mFormat.format(value);
                }
            };
            barDataSet1.setValueFormatter(valueFormatter);
            barDataSet2.setValueFormatter(valueFormatter);

            XAxis xAxis = mBarChart.getXAxis();
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return xValues.get((int)Math.abs(value) % xValues.size());
                }
            });

            BarData data = new BarData(dataSets);
            mBarChart.setData(data);
            mBarChart.setFitBars(true);
            mBarChart.getXAxis().setLabelRotationAngle(-60);
        }
        mBarChart.animateY(2500);
    }


    /**
     * 准备测试数据，测试统计图的显示效果
     */
    private void setTestData(){
        Log.d("Sean", "initData: 准备调用setPieChartData");
        setPieChartData(10,50);

        // for test
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();

        for(int i = 5; i < 35; i+=5){
            list1.add(new Integer(i));
            list2.add(new Integer(i-3));
        }

        dates.add("2017年1月1日");
        dates.add("2017年1月4日");
        dates.add("2017年1月9日");
        dates.add("2017年2月1日");
        dates.add("2017年2月5日");
        dates.add("2017年3月1日");

        setLineChartData(list1,list2);
        setBarChartData(list1,list2,dates);
    }
}
