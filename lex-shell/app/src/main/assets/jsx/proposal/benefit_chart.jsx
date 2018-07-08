class BenefitChart extends React.Component {
  constructor() {
    super()
  }
  componentWillMount() {
    this.state = {
      raw: {
        ml: 80,
        mr: 40,
        mt: 80,
        mb: 130,
        w: this.props.size,
        h: this.props.size/3*2,
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
    ctx.fillText("万", x0, ax.mt / 2)
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
    ctx.textAlign = 'left'
    ctx.textBaseline = 'middle'
    let x = ax.w - ax.mr
    let y = ax.mt / 2
    this.state.chart.data.map(v1 => {
      if (v1.type == "text")
        return
      ctx.fillStyle = "#" + v1.color
      let ww = ctx.measureText(v1.name).width + ax.m + ax.bar
      if (x < ax.ml + ww) {
        x = ax.w - ax.mr
        y += ax.bar + 10
      }
      x -= ww - ax.m
      ctx.fillText(v1.name, x + ax.bar + ax.m, y)
      ctx.fillRect(x, y - ax.bar / 2, ax.bar, ax.bar)
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
    let lw = "130px"
    let bw = (this.props.size>1000?150:120)+"px"
    return (
      <div className="text13 center">
        <canvas id={this.props.id} style={{marginLeft:"5px", width:this.props.size-30+"px", height:this.props.size/3*2+"px"}} width={this.props.size} height={this.props.size/3*2} onTouchStart={this.onTouch.bind(this)} onTouchMove={this.onTouch.bind(this)}></canvas>
        <div style={{display:"flex", marginLeft:"10px", lineHeight:"50px"}}>
          <div style={{width:lw}}>保单年度</div>
          { this.props.years.map(w =>
            <div style={{width:bw, color:w == 0 ? '#008800' : '#aaaaaa'}}>{pos + w >= 0 ? "第" + (pos + w + 1) + "年" : ""}</div>
          )}
        </div>
        <div style={{display:"flex", marginLeft:"10px", lineHeight:"50px"}}>
          <div style={{width:lw}}>期初年龄</div>
          { this.props.years.map(w =>
            <div style={{width:bw, color:w == 0 ? '#008800' : '#aaaaaa'}}>{pos + w >= 0 ? (this.state.chart.age + pos + w) + "岁" : ""}</div>
          )}
        </div>
        { this.state.chart.data.map((v, i) =>
          <div style={{display:"flex", flexDirection:"column", lineHeight:"50px"}}>
            <div style={{display:"flex", marginLeft:"10px"}}>
              <div style={{width:lw}}>{v.name}</div>
              { this.props.years.map(w =>
                <div style={{width:bw, color:w == 0 ? '#008800' : '#aaaaaa'}}>{pos + w >= 0 && pos + w < v.data.length ? v.data[pos + w] : ""}</div>
              )}
            </div>
          </div>
        )}
        <div style={{height:"30px"}}></div>
      </div>      
    )
  }
}

module.exports = BenefitChart
