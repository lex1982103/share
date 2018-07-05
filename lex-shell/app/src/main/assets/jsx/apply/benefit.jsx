class BenefitChart extends React.Component {
  constructor() {
    super()
    this.state = {
      raw: {
        ml: 80,
        mr: 40,
        mt: 80,
        mb: 130,
        w: 750,
        h: 550,
        m: 5, //坐标尺刻度长短
        bar: 20, //bar宽度
        barm: 50, //bar空白
        font: 24,
        text: 24
      },
      pos: 0,
      chart: {
        age: 0,
        data: []
      }
    }
  }
  componentDidMount() {
    let chart = this.props.chart
    this.state.axis = this.state.raw
    this.state.ctx = document.getElementById(this.props.id).getContext("2d")
    this.state.ctx.lineCap = 'round'
    this.state.ctx.lineJoin = 'round'

    if (chart) {
      chart.content.data.map(v => {
        for (let j = 0; j < v.data.length; j++)
          v.data[j] = Math.round(v.data[j])
      });
      this.state.chart = chart.content;
      this.setState({ productName: chart.productName, chart: this.state.chart })
    } else {
      this.state.chart = { age: 0, data: [] }
      this.setState({ productName: "", chart: this.state.chart })
    }
  }
  draw(cx) {
    let ax = this.state.axis;
    let ctx = this.state.ctx;
    ctx.clearRect(0, 0, ax.w, ax.h)

    let xy = this.measure(this.state.chart.data);
    let x0 = ax.ml
    let y0 = ax.h - ax.mb
    let w = ax.w - ax.mr - ax.ml
    let h = ax.h - ax.mb - ax.mt

    //画坐标系标尺
    ctx.lineDash = null
    ctx.lineWidth = 1
    ctx.beginPath()
    ctx.moveTo(x0, y0 - h)
    ctx.lineTo(x0, y0)
    ctx.lineTo(x0 + w, y0)
    ctx.font = ax.font + "px Arial"
    ctx.textAlign = 'right'
    ctx.textBaseline = 'middle'
    ctx.fillText("万", x0, y0 - h * 1.1)
    for (let i = 0; i <= 10; i++) {
      ctx.moveTo(x0, y0 - h * i / 10)
      ctx.lineTo(x0 - ax.m, y0 - h * i / 10)

      let v = xy.y * i / 10 / 10000;
      if (v < 10) {
        v = v.toFixed(1)
      } else {
        v = Math.round(v)
      }
      ctx.fillText(v, x0 - ax.m - 1, y0 - h * i / 10)
    }
    ctx.textAlign = 'center'
    ctx.textBaseline = 'top'
    for (let i = 0; i < xy.x; i += Math.ceil(xy.x / 10)) {
      ctx.moveTo(x0 + w * i / (xy.x - 1), y0)
      ctx.lineTo(x0 + w * i / (xy.x - 1), y0 + ax.m)
      ctx.fillText(this.state.chart.age + i, x0 + w * i / xy.x, y0 + ax.m)
    }
    ctx.strokeStyle = "Black"
    ctx.stroke()

    //画线
    ctx.lineWidth = 3
    this.state.chart.data.map(v1 => {
      if (v1.type == "text")
        return
      ctx.beginPath()
      for (let i = 0; i < xy.x; i++) {
        let x = x0 + i * w / (xy.x - 1);
        if (i == 0) {
          ctx.moveTo(x, y0 - v1.data[i] * h / xy.y)
        } else {
          ctx.lineTo(x, y0 - v1.data[i] * h / xy.y)
        }
      }
      ctx.strokeStyle = "#" + v1.color
      ctx.stroke()
    })

    //画顶部的介绍
    ctx.textAlign = 'right'
    ctx.textBaseline = 'middle'
    let x = ax.w - ax.mr
    this.state.chart.data.map(v1 => {
      if (v1.type == "text")
        return
      ctx.fillStyle = "#" + v1.color
      ctx.fillText(v1.name, x, y0 - h * 1.1)
      x -= ctx.measureText(v1.name).width + ax.m + ax.bar
      ctx.fillRect(x, y0 - h * 1.1 - ax.bar / 2, ax.bar, ax.bar)
      x -= ax.m 
    })

    //计算点击的位置
    let pos = -1;
    if (cx) {
      for (let i = 0; i < xy.x; i++) {
        let x = x0 + i * w / (xy.x - 1);
        if (Math.abs(cx - x) < w / xy.x / 2) {
          pos = i;
          break;
        }
      }
      if (cx > x0 + w)
        pos = xy.x - 1
      else if (cx < x0)
        pos = 0
    }

    //画节点圈
    ctx.lineWidth = 1
    this.state.chart.data.map(v1 => {
      if (v1.type == "text")
        return
      for (let i = 5; i < xy.x; i += 5) {
        ctx.beginPath()
        ctx.arc(x0 + i * w / (xy.x - 1), y0 - v1.data[i] * h / xy.y, 2, 0, Math.PI * 2)
        ctx.fillStyle = "White"
        ctx.fill()
        ctx.strokeStyle = "#" + v1.color
        ctx.stroke()
      }
    })

    //画下部的年龄条
    ctx.fillStyle = "LightGray"
    ctx.fillRect(x0, ax.h - ax.barm - ax.bar, w, ax.bar)
    ctx.textAlign = 'center'
    ctx.textBaseline = 'middle'
    ctx.fillStyle = "Black"
    ctx.font = ax.text + "px"
    ctx.fillText("年龄", x0 / 2, ax.h - ax.barm - ax.bar / 2)

    //画选择线和年龄条的进度
    if (pos >= 0) {
      let posx = x0 + pos * w / (xy.x - 1)
      ctx.beginPath()
      ctx.lineDash = [4, 4]
      ctx.lineDashOffset = ax.m;
      ctx.moveTo(posx, 0)
      ctx.lineTo(posx, ax.h - ax.barm - ax.bar)
      this.state.chart.data.map(v1 => {
        if (v1.type == "text")
          return
        let y = y0 - v1.data[pos] * h / xy.y;
        ctx.moveTo(posx, y)
        ctx.lineTo(x0, y)
      })
      ctx.strokeStyle = "Gray"
      ctx.stroke()

      ctx.fillStyle = "ForestGreen"
      ctx.fillRect(x0, ax.h - ax.barm - ax.bar, posx - x0, ax.bar)

      ctx.textAlign = 'center'
      ctx.textBaseline = 'top'
      ctx.fillStyle = "Black"
      ctx.fillText((this.state.chart.age + pos) + "岁", posx, ax.h - ax.barm)
    }
    
    if (pos >= 0)
      this.setState({ pos: pos })
  }
  measure(vals) {
    let y = 10;
    let x = 2;
    vals.map(v1 => {
      if (v1.type == "text")
        return
      v1.data.map(v2 => {
        if (y < v2)
          y = v2;
      })
      if (x < v1.data.length)
        x = v1.data.length
    })
    return { x: x, y: y };
  }
  translate(ox) {
    let ww = 750
    let s = ww / ox.w
    let r = {}
    for (let i in ox)
      r[i] = ox[i] * s
    return r
  }
  onTouch(e) {
    this.draw(e.changedTouches[0].clientX)
  }
  render() {
    let pos = this.state.pos
    return (
      <div className="text13" style={{backgroundColor:"#fff", textAlign:"center"}}>
        <div class="eclipse text18" style={{width:"690px", textAlign:"center", marginLeft:"30px", height:"80px", lineHeight:"80px", borderBottom:"#ddd solid 1px"}}>
          {this.state.productName}
        </div>
        <canvas id={this.props.id} style={{width:"750px", height:"550px"}} width="750" height="550" onTouchStart={this.onTouch.bind(this)} onTouchMove={this.onTouch.bind(this)}></canvas>
        <div style={{display:"flex", marginLeft:"20px", lineHeight:"50px"}}>
          <div style={{width:"110px"}}>保单年度</div>
          { this.props.years.map(w =>
            <div style={{width:"120px", color:w == 0 ? '#008800' : '#aaaaaa'}}>{pos + w >= 0 ? "第" + (pos + w + 1) + "年" : ""}</div>
          )}
        </div>
        <div style={{display:"flex", marginLeft:"20px", lineHeight:"50px"}}>
          <div style={{width:"110px"}}>期初年龄</div>
          { this.props.years.map(w =>
            <div style={{width:"120px", color:w == 0 ? '#008800' : '#aaaaaa'}}>{pos + w >= 0 ? (this.state.chart.age + pos + w) + "岁" : ""}</div>
          )}
        </div>
        { this.state.chart.data.map((v, i) =>
          <div style={{display:"flex", flexDirection:"column", lineHeight:"50px"}}>
            <div style={{display:"flex", marginLeft:"20px"}}>
              <div style={{width:"110px"}}>{v.name}</div>
              { this.props.years.map(w =>
                <div style={{width:"120px", color:w == 0 ? '#008800' : '#aaaaaa'}}>{pos + w >= 0 && pos + w < v.data.length ? v.data[pos + w] : ""}</div>
              )}
            </div>
          </div>
        )}
        <div style={{height:"30px"}}></div>
      </div>      
    )
  }
}

class Main extends React.Component {
  constructor() {
    super()
    this.state = {
      planId: common.param("planId"),
      mode: 0,
      tabs: ["利益图表", "责任条款"],
      coverage: [],
      chart: []
    }
  }
  componentDidMount() {
    MF.setTitle("利益演示")
    APP.apply.format(this.state.planId, "coverage,benefit_chart", r => {
      this.setState({coverage:r.coverage, chart:r.benefit_chart}, this.onRepaint)
    })
  }
  onModeSwitch(i) {
    this.setState({ mode: i }, this.onRepaint)
  }
  onRepaint() {
    if (this.state.mode == 0) {
      for (let i=0;i<this.state.chart.length;i++) {
        let chart = this.refs["benefitChart"+i]
        if (chart)
          chart.draw(400)
      }
    }
  }
  render() {
    return (
      <div>
        <div style={{display:"flex", width:"750px", position:"fixed", zIndex:"50", top:"0", backgroundColor:"#e6e6e6"}}>
          { this.state.tabs.map((v, i) =>
            <div className={"tab " + (i == this.state.mode ? 'tab-focus' : 'tab-blur')} key={i} style={{width:"250px"}} onClick={this.onModeSwitch.bind(this, i)}>
              <text className="text18">{v}</text>
            </div>
          )}
        </div>
        { 
          this.state.mode == 1 ?
            <div style={{display:"flex", flexDirection:"column", marginTop:"80px"}}>
              { this.state.coverage.map((v, i) => 
                <div className="pl-2 pr-2 bg-white">
                  <div style={{marginTop:(i!=0?10:0)+"px"}}> 
                    <div className="text17 eclipse" style={{width:"690px", textAlign:"center", padding:"10px", lineHeight:"60px", borderBottom:"#ddd solid 1px"}}>
                      {v.productName}
                    </div>
                  </div>
                  { v.content.map((x, j) =>
                    <div style={{background:"#fff"}}>
                      <div> 
                        <text className="text17" style={{margin:"10px", lineHeight:"60px"}}>● {x.title}</text>
                      </div>
                      { x.content.map((y, k) =>
                        <div className="text16">　　{y.text}</div>
                      )}
                      <div style={{height:"10px"}}></div>
                    </div>
                  )}
                  <div style={{height:"10px"}}></div>
                </div>
              )}
            </div>
          : this.state.mode == 0 ?
            <div style={{display:"flex", flexDirection:"column", marginTop:"80px"}}>
              { this.state.chart.map((v, i) => v.content == null ? null :
                <div style={{display:"flex", flexDirection:"column"}}>
                  <BenefitChart ref={"benefitChart"+i} id={"benefitChart"+i} chart={v} years={[-2,-1,0,1,2]}/> 
                  <div style={{height:"10px", backgroundColor:"#e6e6e6"}}/>
                </div>
              )}
            </div>
          : null
        }
      </div>
    )
  }
}

$(document).ready( function() {
  ReactDOM.render(<Main/>, document.getElementById("root"))
})
