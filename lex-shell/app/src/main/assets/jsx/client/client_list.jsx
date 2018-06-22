class ClientList extends React.Component {
    constructor() {
        super();
        this.state = {
            edit: false,
        }
    }
    componentDidMount() {
        window.MF && MF.setTitle("客户管理")
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
                        <h3>所有客户<i>（12人）</i></h3>
                        <a className="cl-edit" onClick={()=>this.setState({edit: !edit})}>{edit ? '取消': '编辑'}</a>
                    </div>
                    <dl className="cl-section">
                        <dt>S</dt>
                        <dd>
                            <a>
                                <span>苏洪涛</span>
                                <i>男</i>
                                <em>1990-03-23</em>
                            </a>
                            <span>
                                <a>编辑</a>
                                <a>删除</a>
                            </span>
                        </dd>
                        <dd className="cl-line"></dd>
                        <dd>
                            <a>
                                <span>苏洪涛</span>
                                <i>男</i>
                                <em>1990-03-23</em>
                            </a>
                            <span>
                                <a>编辑</a>
                                <a>删除</a>
                            </span>
                        </dd>

                    </dl>
                    <dl className="cl-section">
                        <dt>S</dt>
                        <dd>
                            <a>
                                <span>苏洪涛</span>
                                <i>男</i>
                                <em>1990-03-23</em>
                            </a>
                            <span>
                                <a>编辑</a>
                                <a>删除</a>
                            </span>
                        </dd>
                        <dd className="cl-line"></dd>
                        <dd>
                            <a>
                                <span>苏洪涛</span>
                                <i>男</i>
                                <em>1990-03-23</em>
                            </a>
                            <span>
                                <a>编辑</a>
                                <a>删除</a>
                            </span>
                        </dd>

                    </dl>
                    <dl className="cl-section">
                        <dt>S</dt>
                        <dd>
                            <a>
                                <span>苏洪涛</span>
                                <i>男</i>
                                <em>1990-03-23</em>
                            </a>
                            <span>
                                <a>编辑</a>
                                <a>删除</a>
                            </span>
                        </dd>
                        <dd className="cl-line"></dd>
                        <dd>
                            <a>
                                <span>苏洪涛</span>
                                <i>男</i>
                                <em>1990-03-23</em>
                            </a>
                            <span>
                                <a>编辑</a>
                                <a>删除</a>
                            </span>
                        </dd>

                    </dl>
                    <dl className="cl-section">
                        <dt>S</dt>
                        <dd>
                            <a>
                                <span>苏洪涛</span>
                                <i>男</i>
                                <em>1990-03-23</em>
                            </a>
                            <span>
                                <a>编辑</a>
                                <a>删除</a>
                            </span>
                        </dd>
                        <dd className="cl-line"></dd>
                        <dd>
                            <a>
                                <span>苏洪涛</span>
                                <i>男</i>
                                <em>1990-03-23</em>
                            </a>
                            <span>
                                <a>编辑</a>
                                <a>删除</a>
                            </span>
                        </dd>

                    </dl>
                    <dl className="cl-section">
                        <dt>S</dt>
                        <dd>
                            <a>
                                <span>苏洪涛</span>
                                <i>男</i>
                                <em>1990-03-23</em>
                            </a>
                            <span>
                                <a>编辑</a>
                                <a>删除</a>
                            </span>
                        </dd>
                        <dd className="cl-line"></dd>
                        <dd>
                            <a>
                                <span>苏洪涛</span>
                                <i>男</i>
                                <em>1990-03-23</em>
                            </a>
                            <span>
                                <a>编辑</a>
                                <a>删除</a>
                            </span>
                        </dd>

                    </dl>
                    <dl className="cl-section" id="secA">
                        <dt>S</dt>
                        <dd>
                            <a>
                                <span>苏洪涛</span>
                                <i>男</i>
                                <em>1990-03-23</em>
                            </a>
                            <span>
                                <a>编辑</a>
                                <a>删除</a>
                            </span>
                        </dd>
                        <dd className="cl-line"></dd>
                        <dd>
                            <a>
                                <span>苏洪涛</span>
                                <i>男</i>
                                <em>1990-03-23</em>
                            </a>
                            <span>
                                <a>编辑</a>
                                <a>删除</a>
                            </span>
                        </dd>

                    </dl>

                </div>
                <div className="c-alphabet">
                    <a href="javascript:void(0)" onClick={()=>this.onAlphaClick('secA')}>A</a>
                    <a>B</a>
                    <a>C</a>
                    <a>D</a>
                    <a>E</a>
                </div>
                <div className="c-footer">
                    <a href="javascript:void(0)">新建客户</a>
                </div>
            </div>
        )
    }
}

$(document).ready( function() {
    ReactDOM.render(<ClientList/>, document.getElementById("root"))
})