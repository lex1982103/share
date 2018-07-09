import Swiper from '../components/Swiper.jsx'
const serverUrl = 'http://114.112.96.61:7666/';
class Main extends React.Component {
    constructor() {
        super();
        this.state = {
            images: [],
            searchInputting: false,
            searchText: '',
            products: [],
            footNav:[
                '首页','个人管理','团队管理','我的'
            ],
            LabelDta:'',
            orgId: common.param("orgId")
        }
    }
    componentDidMount() {
        let that = this
        window.MF && MF.setTitle("首页")
        this.fetchBanner();
        this.fetchProduct();

        $.ajax({
            url: 'http://114.112.96.61:7666/app/user/qrymodule.json',
            type: "POST",
            data: {
                "orgId":"10200"},
            success:(data) => {
                console.log(JSON.stringify(data))
                that.setState({
                    LabelDta:data.content
                })
            },
            fail: function(r) {
            },
            dataType:"json"
        });
    }

    fetchProduct(){
        $.ajax({
            url: serverUrl + 'app/cms/article/list.json',
            type: "POST",
            data: JSON.stringify({
                "from":0,
                "number":10,
                "state":1}),
            success:(r) => {
                this.setState({
                    products: r.content.list || []
                })
            },
            fail: function(r) {
            },
            dataType:"json"
        });
    }

    fetchBanner(){
        $.ajax({
            url: serverUrl + 'app/cms/adver/list.json',
            type: "POST",
            data: JSON.stringify({
                "from":0,
                "number":10,
                "state":1}),
            success:(r) => {
                if (r.result == "success") {
                    let imageData = r.content.list.map(row=>{
                        console.log(row)
                        return {
                            url: serverUrl + row.url,
                            link: serverUrl + row.link
                        }
                    })
                    this.setState({
                        images: imageData
                    });
                }

            },
            fail: function(r) {
            },
            dataType:"json"
        });
    }
    openApply(orderId) {
        window.MF && MF.navi("apply/start.html?orderId=" + orderId)
    }
    newApply() {
        window.MF && MF.navi("apply/start.html")
    }
    openProposal() {
        window.MF && MF.navi("proposal/proposal_list.html")
    }
    openCustomer() {
        window.MF &&  MF.navi("client/client_list.html")
    }
    productDetail(prod){
        localStorage.productData = JSON.stringify(prod)
       location.href = '../productDetail/productDetail.html'
    }
    toPage(index){
        if(index == 3){
            location.href = 'mine.html'
        }
    }
    toFunPage(url){
        location.href = url + '.html'
    }
    render() {
        return (
            <div className="home-container">
                <div className="banner-wrap">
                    <Swiper images={this.state.images}/>
                    {/*<div className="banner-header">
                        <div className="banner-logo">
                            <img src="../images/home/logo.png"/>
                        </div>
                        <div className="banner-search">
                            {
                                !this.state.searchInputting && !this.state.searchText && (
                                    <div>
                                        <span><img src="../images/home/search.png"/></span><span>搜索产品 / 客户信息</span>
                                    </div>
                                )
                            }

                            <input onFocus={()=>this.setState({searchInputting: true})} onBlur={()=>this.setState({searchInputting: false})} onChange={(e)=>this.setState({searchText: e.target.value})}/>
                        </div>
                        <div className="banner-msg">
                            <img src="../images/home/message.png"/>
                            <em></em>
                        </div>
                    </div>*/}
                </div>
                <div className="shortcut-row1">
                    {
                        this.state.LabelDta&&this.state.LabelDta.map((prod)=>{
                           return(
                               <a className="srow-item" href={prod.link}>
                                   <div>
                                       <img src={prod.img}/>
                                   </div>
                                   <span>{prod.name}</span>
                               </a>
                           )
                        })
                    }
                </div>
                <div className="shortcut-row2">
                    <a className="sr2-left" onClick={this.toFunPage.bind(this,'CompanyIntroduction')}>
                        <img src="../images/home/company.png"/>
                        <span>公司介绍</span>
                    </a>
                    <div className="sr2-right" onClick={this.toFunPage.bind(this,'productIntroduction')}>
                        <a class="sr2-top">
                            <img src="../images/home/product.png"/>
                            <span>产品介绍</span>
                        </a>
                        <div class="sr2-bottom">
                            <a>
                                <span>自主经营</span>
                                <img src="../images/home/independent.png"/>
                            </a>
                            <a>
                                <span>展示夹</span>
                                <img src="../images/home/showHome.png"/>
                            </a>
                        </div>
                    </div>
                </div>
                <div className="promote">
                    <div className="p-title">
                        <span>热销推荐</span>
                    </div>
                    <div className="p-content">
                        {
                            this.state.products.map(prod=>{
                                return (
                                    <a className="prod-item"onClick={this.productDetail.bind(this,prod)}>
                                        <img src={ prod.cover ? (serverUrl + prod.cover) :  "../images/home/default_img.png"}/>
                                        <i>{prod.title}</i>
                                    </a>
                                )
                            })
                        }
                    </div>

                </div>
                {/*<div className="btn-fl text18 tc-white bg-primary" onClick={this.newApply.bind(this)}>新的投保</div>*/}
                {/*<div className="btn-fl text18 tc-white bg-primary" onClick={this.openApply.bind(this, 40066)}>打开测试投保单</div>*/}
                <ul className="footNav">
                    {
                        this.state.footNav.map((prod,index)=>{
                            if(index==0){
                                return(
                                    <li className="actHome" onClick={this.toPage.bind(this,index)}>{prod}</li>
                                )
                            } else if (index == 1) {
                                return <li className="actHome" onClick={this.openApply.bind(this, 40066)}>{prod}</li>
                            } else if (index == 2) {
                                return <li className="actHome" onClick={this.newApply.bind(this)}>{prod}</li>
                            }else if(index==3){
                                return(
                                    <li className="actHome" onClick={this.toPage.bind(this,index)}>{prod}</li>
                                )
                            }else{
                                return(
                                    <li>{prod}</li>
                                )
                            }

                        })
                    }
                </ul>

            </div>
        )
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})