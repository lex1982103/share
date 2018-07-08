import BenefitChart from './benefit_chart.jsx'

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
    APP.proposal.format(this.state.planId, "coverage,benefit_chart", r => {
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
        <div style={{display:"flex", width:"100%", position:"fixed", zIndex:"50", top:"0", backgroundColor:"#e6e6e6"}}>
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
                    <div className="text17 eclipse" style={{width:SIZE-60+"px", textAlign:"center", padding:"10px", lineHeight:"60px", borderBottom:"#ddd solid 1px"}}>
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
                <div className="pl-1 bg-white">
                  <div class="eclipse text18" style={{width:SIZE-90+"px", textAlign:"center", marginLeft:"35px", height:"80px", lineHeight:"80px", borderBottom:"#ddd solid 1px"}}>
                    {v.productName}
                  </div>
                  <BenefitChart size={SIZE} ref={"benefitChart"+i} id={"benefitChart"+i} chart={v} years={SIZE>1000?[-4,-3,-2,-1,0,1,2,3,4]:[-2,-1,0,1,2]}/>
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

$(document).ready(() => {
  ReactDOM.render(<Main/>, document.getElementById("root"))
})
