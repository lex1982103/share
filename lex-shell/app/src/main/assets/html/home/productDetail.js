var MyScroll = null;
// 记录是否要加载更多
var isLoadMore = false;

class ProductDetail extends React.Component {
    componentWillMount() {
        if (!this.props.cofig || !this.props.cofig.loadMore || !this.props.cofig.data) {
            console.warn('PageView缺少必要的参数cofig,loadMore,data');
        }
    }
    componentDidMount(){

        var _that = this;
        // 自定义id，防止重复
        var elemt = document.getElementById(this.props.cofig.id || 'wrapper');
        var more = document.getElementById((this.props.cofig.id || 'wrapper') + '_more');

        MyScroll = new IScroll(elemt, {
            probeType: 3,
            scrollbars: true, //是否显示默认滚动条
            fadeScrollbars: true, // 是否渐隐滚动条，关掉可以加速
            mouseWheel: true, //是否监听鼠标滚轮事件。
            preventDefault: true, //是否屏蔽默认事件。
            momentum: true, //是否开启动量动画，关闭可以提升效率。
            // keyBindings: true, //绑定按键事件。
            shrinkScrollbars: true,
            click: true,
        });


        MyScroll.on('scroll', function() {

            var yMove = (-this.y - (-this.maxScrollY)); //y轴偏移量
            var maxMove = 70;

            // 已经全部加载
            if (_that.props.cofig.isNoMore) return;

            if (!isLoadMore) {
                if (yMove > 0 && yMove < maxMove) {
                    more.innerHTML = '上拉加载更多';
                    more.style.display = 'block';
                } else if (yMove >= maxMove) {
                    more.innerHTML = '松开立即加载';
                    more.style.display = 'block';
                    isLoadMore = true;
                } else {
                    more.style.display = 'none';
                }
            } else {

                if (yMove < maxMove / 2) {
                    more.innerHTML = '正在加载';
                    more.style.display = 'block';
                }

            }

        })

        MyScroll.on('scrollEnd', function() {

            // 是否加载更多
            if (isLoadMore && !_that.props.cofig.isNoMore) {

                more.innerHTML = '';
                more.style.display = 'none';

                if (_that.props.cofig && _that.props.cofig.loadMore) {
                    _that.props.cofig.loadMore();
                }

            }

        })

        // 阻止默认touchmove事件
        elemt.addEventListener('touchmove', function(e) { e.preventDefault(); }, false);

    }
    // 监听dom更新
    componentDidUpdate() {
        // 刷新myScroll，重新计算高度
        setTimeout(function() {
            MyScroll.refresh();
        }, 100);

        // 重置是否加载更多
        isLoadMore = false;

    }
    componentWillUnmount() {
        // 移除componnet后销毁MyScroll
        MyScroll.destroy();
    }
    render() {

        return (
            <div style={{'height':'100%','width':'100%'}}>
                <div style={{'position':'relative','height':'100%','width':'100%','overflow':'hidden'}}>
                    <div id={'wrapper'} style={{'position': 'absolute','top': '0','bottom': '0','left': '0','right': '0'}}>
                        <div className="w-full">
                            {this.props.cofig.data}
                            <div id={(this.props.cofig.id || 'wrapper') + '_more'} style={{'textAlign':'center','padding':'3px 0px','display':'none'}} ></div>
                            {
                                this.props.cofig.data&&this.props.cofig.data.length<1?(<div className="text-center p-md">无数据</div>):''
                            }
                            {
                                this.props.cofig.data&&(Array.isArray(this.props.cofig.data)?(this.props.cofig.data.length>0):true)&&this.props.cofig.isNoMore?(<div className="text-center p-md">已经全部加载</div>):''
                            }
                        </div>
                    </div>
                </div>
            </div>
        )

    }
}

ReactDOM.render(<ProductDetail/>, document.getElementById("root"))
