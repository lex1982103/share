const serverUrl = 'http://47.104.13.159:31001'
class ClientList extends React.Component {
    constructor() {
        super();
        this.state = {
            edit: false,
            clientCount: 4,
            clientList: [
                {
                    alpha: 'Z',
                    list: [
                        {
                            name: '钟大伟',
                        },
                        {
                            name: '张大全'
                        }
                    ]
                },
                {
                    alpha: 'L',
                    list: [
                        {
                            name: '李欣欣'
                        },
                        {
                            name: '赖星星'
                        }
                    ]
                }
            ]
        }
    }
    componentDidMount() {
        window.MF && MF.setTitle("客户管理")

        this.fetchClientList();
    }

    fetchClientList(){
        const mockData = [
            {
                name: '信东',
            },
            {
                name: '马成虎',
            },
            {
                name: '任超'
            },
            {
                name: '范若宇'
            },
            {
                name: '叶晓琪'
            }
        ]
        /** 按首字母分组 */
        const data = mockData.map(d=>{
            let pinyinStr = pinyinUtil.getFirstLetter(d.name, false);
            let firstAlpha = pinyinStr.substring(0, 1);
            console.log('speel', firstAlpha.toUpperCase());
            d.alpha = firstAlpha.toUpperCase()
            return d;
        })
        let sortArr = []
        data.map(d=>{
            // 检查该字母是否已处理过
            if(!sortArr.filter(item=>item.alpha === d.alpha).length){
                let alphaObj = {
                    alpha: d.alpha
                };
                let arr = data.filter(item=>item.alpha === d.alpha);
                alphaObj.list = arr;
                sortArr.push(alphaObj);
            }

        })
        /** 按首字母排序 */
        for(let i = 0; i < sortArr.length; i++){
            for(let j = i+1; j < data.length; j++){
                if(sortArr[i].alpha > data[j].alpha){
                    let temp = sortArr[i];
                    sortArr[i] = sortArr[j];
                    sortArr[j] = temp;
                }
            }
        }

        this.setState({
            clientList: sortArr
        })
        // $.ajax({
        //     url: serverUrl + '/customerInfo/QueryCustomerList',
        //     type:"POST",
        //     contentType: 'application/json',
        //     data: {
        //         "endTime": "2018-06-30 00:00:00",
        //         "name": "",
        //         "startTime": "2018-06-01 00:00:00"
        //     },
        //     xhrFields: { withCredentials: false },
        //     success:(r) => {
        //         console.log(r)
        //
        //     },
        //     fail: function(r) {
        //     },
        //     dataType:"json"
        // });
    }
    onAlphaClick(id){
        this.props.onAlphaClick && this.props.onAlphaClick(key);
        let el=document.getElementById(id);
        el.scrollIntoView();

    }
    render(){
        const {
            edit
        } = this.state;
        return (
            <div className="client-container">
                <div className="remind-wrap">
                    <a className="remind-birthday">
                        <img src="../images/client/remind-birthday.png"/>
                        <span>生日提醒</span>
                        <em>2</em>
                    </a>
                    <span></span>
                    <a className="remind-renew">
                        <img src="../images/client/remind-renew.png"/>
                        <span>续期提醒</span>
                    </a>
                </div>
                <div className="c-list">
                    <div className="cl-title">
                        <h3>所有客户<i>（{this.state.clientCount}人）</i></h3>
                        <a className="cl-edit" onClick={()=>this.setState({edit: !edit})}>{edit ? '取消': '编辑'}</a>
                    </div>
                    {
                        this.state.clientList.map(item=>{
                            return (
                                <dl className="cl-section" id={'sec'+item.alpha}>
                                    <dt>{item.alpha}</dt>
                                    {
                                        item.list.map(c=>{
                                            return (

                                                <dd>
                                                    <a>
                                                        <span>{c.name}</span>
                                                        <i>男</i>
                                                        <em>1990-03-23</em>
                                                    </a>
                                                    {
                                                        this.state.edit && (
                                                            <span>
                                                                <a>编辑</a>
                                                                <a>删除</a>
                                                            </span>
                                                        )
                                                    }
                                                    <span className="cl-line"></span>
                                                </dd>

                                            )
                                        })
                                    }

                                </dl>

                            )
                        })
                    }
                </div>
                <div className="c-alphabet">
                    {
                        this.state.clientList.map(c=>{
                            return (
                                <a href="javascript:void(0)" onClick={()=>this.onAlphaClick('sec' + c.alpha)}>{c.alpha}</a>
                            )
                        })
                    }

                </div>
                <div className="c-footer">
                    <a href="create_client.html">新建客户</a>
                </div>
            </div>
        )
    }
}

$(document).ready( function() {
    ReactDOM.render(<ClientList/>, document.getElementById("root"))
})