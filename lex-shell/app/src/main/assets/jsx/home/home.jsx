import Swiper from '../components/Swiper'


const serverUrl = 'http://47.104.13.159/boot';
class Main extends React.Component {
    constructor() {
        super();
        this.state = {
            images: [],
            searchInputting: false,
            searchText: '',
            products: []
        }
    }
    componentDidMount() {
        window.MF && MF.setTitle("首页")
        this.fetchBanner();
        this.fetchProduct();
    }

    fetchProduct(){
        $.ajax({
            url: serverUrl + '/client/queryPage',
            type:"GET",
            xhrFields: { withCredentials: false },
            success:(r) => {
                this.setState({
                    products: r.rows || []
                })
            },
            fail: function(r) {
            },
            dataType:"json"
        });
    }

    fetchBanner(){
        $.ajax({
            url: serverUrl + '/client/queryAdverPage',
            type:"GET",
            xhrFields: { withCredentials: false },
            success:(r) => {
                let imageData = r.rows.map(row=>{
                    return {
                        url: serverUrl + row.url,
                        link: serverUrl + row.link
                    }
                })
                this.setState({
                    images: imageData
                })
                console.log(r);
            },
            fail: function(r) {
            },
            dataType:"json"
        });
    }

    openApply(orderId) {
        window.MF &&  MF.navi("apply/start.html?orderId=" + orderId)
    }
    newApply() {
        window.MF &&  MF.navi("apply/start.html")
    }
    openProposal() {
        window.MF && MF.navi("proposal/plan.html")
    }
    render() {
        return (
            <div className="home-container">
                <div className="banner-wrap">
                    <Swiper images={this.state.images}/>
                    <div className="banner-header">
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
                    </div>
                </div>
                <div className="shortcut-row1">
                    <a className="srow-item" href="javascript:void(0)" onClick={this.newApply.bind(this)}>
                        <div>
                            <img src="../images/home/oneKey.png"/>
                        </div>
                        <span>一键投保</span>
                    </a>
                    <a className="srow-item" href="javascript:void(0)" onClick={this.openProposal.bind(this)}>
                        <div>
                            <img src="../images/home/proposal.png"/>
                        </div>
                        <span>建议书</span>
                    </a>
                    <a className="srow-item" href="javascript:void(0)" onClick={this.openApply.bind(this, 40066)}>
                        <div>
                            <img src="../images/home/insurePolicy.png"/>
                        </div>
                        <span>投保单</span>
                    </a>
                    <a className="srow-item">
                        <div>
                            <img src="../images/home/insure.png"/>
                        </div>
                        <span>投保e办理</span>
                    </a>
                    <a className="srow-item">
                        <div>
                            <img src="../images/home/customer.png"/>
                        </div>
                        <span>客户管理</span>
                    </a>
                </div>
                <div className="shortcut-row2">
                    <a className="sr2-left">
                        <img src="../images/home/company.png"/>
                        <span>公司介绍</span>
                    </a>
                    <div className="sr2-right">
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
                        <a>查看更多 &gt;</a>
                    </div>
                    <div className="p-content">
                        {
                            this.state.products.map(prod=>{
                                return (
                                    <a className="prod-item">
                                        <img src={ prod.logo ? (serverUrl + prod.logo) :  "../images/home/default_img.png"}/>
                                        <span>{prod.abbrName}</span>
                                        <i>{prod.name}</i>
                                    </a>
                                )
                            })
                        }
                    </div>

                </div>
                {/*<div className="btn-fl text18 tc-white bg-primary" onClick={this.newApply.bind(this)}>新的投保</div>*/}
                {/*<div className="btn-fl text18 tc-white bg-primary" onClick={this.openApply.bind(this, 40066)}>打开测试投保单</div>*/}


            </div>
        )
    }
}

$(document).ready( function() {
    ReactDOM.render(<Main/>, document.getElementById("root"))
})