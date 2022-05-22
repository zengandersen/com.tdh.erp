/**
 * fa鍥炬爣閫夋嫨鍣� 鏍规嵁寮€婧愰」鐩甴ttps://gitee.com/wujiawei0926/iconpicker淇敼鑰屾潵
 * @author wujiawei0926@yeah.net chung@99php.cn
 * @version 1.1
 */

layui.define(['laypage', 'form'], function (exports) {
    "use strict";

    var IconPicker = function () {
            this.v = '1.1';
        }, _MOD = 'iconPickerFa',
        _this = this,
        $ = layui.jquery,
        laypage = layui.laypage,
        form = layui.form,
        BODY = 'body',
        TIPS = '璇烽€夋嫨鍥炬爣';

    /**
     * 娓叉煋缁勪欢
     */
    IconPicker.prototype.render = function (options) {
        var opts = options,
            // DOM閫夋嫨鍣�
            elem = opts.elem,
            // 鏁版嵁绫诲瀷锛歠ontClass/unicode
            url = opts.url,
            // 鏄惁鍒嗛〉锛歵rue/false
            page = opts.page == null ? true : opts.page,
            // 姣忛〉鏄剧ず鏁伴噺
            limit = opts.limit == null ? 12 : opts.limit,
            // 鏄惁寮€鍚悳绱細true/false
            search = opts.search == null ? true : opts.search,
            // 姣忎釜鍥炬爣鏍煎瓙鐨勫搴︼細'43px'鎴�'20%'
            cellWidth = opts.cellWidth,
            // 鐐瑰嚮鍥炶皟
            click = opts.click,
            // 娓叉煋鎴愬姛鍚庣殑鍥炶皟
            success = opts.success,
            // json鏁版嵁
            data = {},
            // 鍞竴鏍囪瘑
            tmp = new Date().getTime(),
            // 鍒濆鍖栨椂input鐨勫€�
            ORIGINAL_ELEM_VALUE = $(elem).val(),
            TITLE = 'layui-select-title',
            TITLE_ID = 'layui-select-title-' + tmp,
            ICON_BODY = 'layui-iconpicker-' + tmp,
            PICKER_BODY = 'layui-iconpicker-body-' + tmp,
            PAGE_ID = 'layui-iconpicker-page-' + tmp,
            LIST_BOX = 'layui-iconpicker-list-box',
            selected = 'layui-form-selected',
            unselect = 'layui-unselect';

        var a = {
            init: function () {
                data = common.getData(url);

                a.hideElem().createSelect().createBody().toggleSelect();
                a.preventEvent().inputListen();
                common.loadCss();

                if (success) {
                    success(this.successHandle());
                }

                return a;
            },
            successHandle: function () {
                var d = {
                    options: opts,
                    data: data,
                    id: tmp,
                    elem: $('#' + ICON_BODY)
                };
                return d;
            },
            /**
             * 闅愯棌elem
             */
            hideElem: function () {
                $(elem).hide();
                return a;
            },
            /**
             * 缁樺埗select涓嬫媺閫夋嫨妗�
             */
            createSelect: function () {
                var oriIcon = '<i class="fa">';

                // 榛樿鍥炬爣
                if (ORIGINAL_ELEM_VALUE === '') {
                    ORIGINAL_ELEM_VALUE = 'fa-adjust';

                }


                oriIcon = '<i class="fa ' + ORIGINAL_ELEM_VALUE + '">';

                oriIcon += '</i>';

                var selectHtml = '<div class="layui-iconpicker layui-unselect layui-form-select" id="' + ICON_BODY + '">' +
                    '<div class="' + TITLE + '" id="' + TITLE_ID + '">' +
                    '<div class="layui-iconpicker-item">' +
                    '<span class="layui-iconpicker-icon layui-unselect">' +
                    oriIcon +
                    '</span>' +
                    '<i class="layui-edge"></i>' +
                    '</div>' +
                    '</div>' +
                    '<div class="layui-anim layui-anim-upbit" style="">' +
                    '123' +
                    '</div>';
                $(elem).after(selectHtml);
                return a;
            },
            /**
             * 灞曞紑/鎶樺彔涓嬫媺妗�
             */
            toggleSelect: function () {
                var item = '#' + TITLE_ID + ' .layui-iconpicker-item,#' + TITLE_ID + ' .layui-iconpicker-item .layui-edge';
                a.event('click', item, function (e) {
                    var $icon = $('#' + ICON_BODY);
                    if ($icon.hasClass(selected)) {
                        $icon.removeClass(selected).addClass(unselect);
                    } else {
                        // 闅愯棌鍏朵粬picker
                        $('.layui-form-select').removeClass(selected);
                        // 鏄剧ず褰撳墠picker
                        $icon.addClass(selected).removeClass(unselect);
                    }
                    e.stopPropagation();
                });
                return a;
            },
            /**
             * 缁樺埗涓讳綋閮ㄥ垎
             */
            createBody: function () {
                // 鑾峰彇鏁版嵁
                var searchHtml = '';

                if (search) {
                    searchHtml = '<div class="layui-iconpicker-search">' +
                        '<input class="layui-input">' +
                        '<i class="layui-icon">&#xe615;</i>' +
                        '</div>';
                }

                // 缁勫悎dom
                var bodyHtml = '<div class="layui-iconpicker-body" id="' + PICKER_BODY + '">' +
                    searchHtml +
                    '<div class="' + LIST_BOX + '"></div> ' +
                    '</div>';
                $('#' + ICON_BODY).find('.layui-anim').eq(0).html(bodyHtml);
                a.search().createList().check().page();

                return a;
            },
            /**
             * 缁樺埗鍥炬爣鍒楄〃
             * @param text 妯＄硦鏌ヨ鍏抽敭瀛�
             * @returns {string}
             */
            createList: function (text) {
                var d = data,
                    l = d.length,
                    pageHtml = '',
                    listHtml = $('<div class="layui-iconpicker-list">')//'<div class="layui-iconpicker-list">';

                // 璁＄畻鍒嗛〉鏁版嵁
                var _limit = limit, // 姣忛〉鏄剧ず鏁伴噺
                    _pages = l % _limit === 0 ? l / _limit : parseInt(l / _limit + 1), // 鎬昏澶氬皯椤�
                    _id = PAGE_ID;

                // 鍥炬爣鍒楄〃
                var icons = [];

                for (var i = 0; i < l; i++) {
                    var obj = d[i];

                    // 鍒ゆ柇鏄惁妯＄硦鏌ヨ
                    if (text && obj.indexOf(text) === -1) {
                        continue;
                    }

                    // 鏄惁鑷畾涔夋牸瀛愬搴�
                    var style = '';
                    if (cellWidth !== null) {
                        style += ' style="width:' + cellWidth + '"';
                    }

                    // 姣忎釜鍥炬爣dom
                    var icon = '<div class="layui-iconpicker-icon-item" title="' + obj + '" ' + style + '>';

                    icon += '<i class="fa ' + obj + '"></i>';

                    icon += '</div>';

                    icons.push(icon);
                }

                // 鏌ヨ鍑哄浘鏍囧悗鍐嶅垎椤�
                l = icons.length;
                _pages = l % _limit === 0 ? l / _limit : parseInt(l / _limit + 1);
                for (var i = 0; i < _pages; i++) {
                    // 鎸塴imit鍒嗗潡
                    var lm = $('<div class="layui-iconpicker-icon-limit" id="layui-iconpicker-icon-limit-' + tmp + (i + 1) + '">');

                    for (var j = i * _limit; j < (i + 1) * _limit && j < l; j++) {
                        lm.append(icons[j]);
                    }

                    listHtml.append(lm);
                }

                // 鏃犳暟鎹�
                if (l === 0) {
                    listHtml.append('<p class="layui-iconpicker-tips">鏃犳暟鎹�</p>');
                }

                // 鍒ゆ柇鏄惁鍒嗛〉
                if (page) {
                    $('#' + PICKER_BODY).addClass('layui-iconpicker-body-page');
                    pageHtml = '<div class="layui-iconpicker-page" id="' + PAGE_ID + '">' +
                        '<div class="layui-iconpicker-page-count">' +
                        '<span id="' + PAGE_ID + '-current">1</span>/' +
                        '<span id="' + PAGE_ID + '-pages">' + _pages + '</span>' +
                        ' (<span id="' + PAGE_ID + '-length">' + l + '</span>)' +
                        '</div>' +
                        '<div class="layui-iconpicker-page-operate">' +
                        '<i class="layui-icon" id="' + PAGE_ID + '-prev" data-index="0" prev>&#xe603;</i> ' +
                        '<i class="layui-icon" id="' + PAGE_ID + '-next" data-index="2" next>&#xe602;</i> ' +
                        '</div>' +
                        '</div>';
                }


                $('#' + ICON_BODY).find('.layui-anim').find('.' + LIST_BOX).html('').append(listHtml).append(pageHtml);
                return a;
            },
            // 闃绘Layui鐨勪竴浜涢粯璁や簨浠�
            preventEvent: function () {
                var item = '#' + ICON_BODY + ' .layui-anim';
                a.event('click', item, function (e) {
                    e.stopPropagation();
                });
                return a;
            },
            // 鍒嗛〉
            page: function () {
                var icon = '#' + PAGE_ID + ' .layui-iconpicker-page-operate .layui-icon';

                $(icon).unbind('click');
                a.event('click', icon, function (e) {
                    var elem = e.currentTarget,
                        total = parseInt($('#' + PAGE_ID + '-pages').html()),
                        isPrev = $(elem).attr('prev') !== undefined,
                        // 鎸夐挳涓婃爣鐨勯〉鐮�
                        index = parseInt($(elem).attr('data-index')),
                        $cur = $('#' + PAGE_ID + '-current'),
                        // 鐐瑰嚮鏃舵鍦ㄦ樉绀虹殑椤电爜
                        current = parseInt($cur.html());

                    // 鍒嗛〉鏁版嵁
                    if (isPrev && current > 1) {
                        current = current - 1;
                        $(icon + '[prev]').attr('data-index', current);
                    } else if (!isPrev && current < total) {
                        current = current + 1;
                        $(icon + '[next]').attr('data-index', current);
                    }
                    $cur.html(current);

                    // 鍥炬爣鏁版嵁
                    $('#' + ICON_BODY + ' .layui-iconpicker-icon-limit').hide();
                    $('#layui-iconpicker-icon-limit-' + tmp + current).show();
                    e.stopPropagation();
                });
                return a;
            },
            /**
             * 鎼滅储
             */
            search: function () {
                var item = '#' + PICKER_BODY + ' .layui-iconpicker-search .layui-input';
                a.event('input propertychange', item, function (e) {
                    var elem = e.target,
                        t = $(elem).val();
                    a.createList(t);
                });
                return a;
            },
            /**
             * 鐐瑰嚮閫変腑鍥炬爣
             */
            check: function () {
                var item = '#' + PICKER_BODY + ' .layui-iconpicker-icon-item';
                a.event('click', item, function (e) {
                    var el = $(e.currentTarget).find('.fa'),
                        icon = '';

                    var clsArr = el.attr('class').split(/[\s\n]/),
                        cls = clsArr[1],
                        icon = cls;
                    $('#' + TITLE_ID).find('.layui-iconpicker-item .fa').html('').attr('class', clsArr.join(' '));


                    $('#' + ICON_BODY).removeClass(selected).addClass(unselect);
                    $(elem).val(icon).attr('value', icon);
                    // 鍥炶皟
                    if (click) {
                        click({
                            icon: icon
                        });
                    }

                });
                return a;
            },
            // 鐩戝惉鍘熷input鏁板€兼敼鍙�
            inputListen: function () {
                var el = $(elem);
                a.event('change', elem, function () {
                    var value = el.val();
                })
                // el.change(function(){

                // });
                return a;
            },
            event: function (evt, el, fn) {
                $(BODY).on(evt, el, fn);
            }
        };

        var common = {
            /**
             * 鍔犺浇鏍峰紡琛�
             */
            loadCss: function () {
                var css = '.layui-iconpicker {max-width: 280px;}.layui-iconpicker .layui-anim{display:none;position:absolute;left:0;top:42px;padding:5px 0;z-index:899;min-width:100%;border:1px solid #d2d2d2;max-height:300px;overflow-y:auto;background-color:#fff;border-radius:2px;box-shadow:0 2px 4px rgba(0,0,0,.12);box-sizing:border-box;}.layui-iconpicker-item{border:1px solid #e6e6e6;width:90px;height:38px;border-radius:4px;cursor:pointer;position:relative;}.layui-iconpicker-icon{border-right:1px solid #e6e6e6;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;display:block;width:60px;height:100%;float:left;text-align:center;background:#fff;transition:all .3s;}.layui-iconpicker-icon i{line-height:38px;font-size:18px;}.layui-iconpicker-item > .layui-edge{left:70px;}.layui-iconpicker-item:hover{border-color:#D2D2D2!important;}.layui-iconpicker-item:hover .layui-iconpicker-icon{border-color:#D2D2D2!important;}.layui-iconpicker.layui-form-selected .layui-anim{display:block;}.layui-iconpicker-body{padding:6px;}.layui-iconpicker .layui-iconpicker-list{background-color:#fff;border:1px solid #ccc;border-radius:4px;}.layui-iconpicker .layui-iconpicker-icon-item{display:inline-block;width:21.1%;line-height:36px;text-align:center;cursor:pointer;vertical-align:top;height:36px;margin:4px;border:1px solid #ddd;border-radius:2px;transition:300ms;}.layui-iconpicker .layui-iconpicker-icon-item i.layui-icon{font-size:17px;}.layui-iconpicker .layui-iconpicker-icon-item:hover{background-color:#eee;border-color:#ccc;-webkit-box-shadow:0 0 2px #aaa,0 0 2px #fff inset;-moz-box-shadow:0 0 2px #aaa,0 0 2px #fff inset;box-shadow:0 0 2px #aaa,0 0 2px #fff inset;text-shadow:0 0 1px #fff;}.layui-iconpicker-search{position:relative;margin:0 0 6px 0;border:1px solid #e6e6e6;border-radius:2px;transition:300ms;}.layui-iconpicker-search:hover{border-color:#D2D2D2!important;}.layui-iconpicker-search .layui-input{cursor:text;display:inline-block;width:86%;border:none;padding-right:0;margin-top:1px;}.layui-iconpicker-search .layui-icon{position:absolute;top:11px;right:4%;}.layui-iconpicker-tips{text-align:center;padding:8px 0;cursor:not-allowed;}.layui-iconpicker-page{margin-top:6px;margin-bottom:-6px;font-size:12px;padding:0 2px;}.layui-iconpicker-page-count{display:inline-block;}.layui-iconpicker-page-operate{display:inline-block;float:right;cursor:default;}.layui-iconpicker-page-operate .layui-icon{font-size:12px;cursor:pointer;}.layui-iconpicker-body-page .layui-iconpicker-icon-limit{display:none;}.layui-iconpicker-body-page .layui-iconpicker-icon-limit:first-child{display:block;}';
                var $style = $('head').find('style[iconpicker]');
                if ($style.length === 0) {
                    $('head').append('<style rel="stylesheet" iconpicker>' + css + '</style>');
                }
            },

            /**
             * 鑾峰彇鏁版嵁
             */
            getData: function (url) {
                var iconlist = [];
                $.ajax({
                    url: url,
                    type: 'get',
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    async: false,
                    success: function (ret) {
                        var exp = /fa-var-(.*):/ig;
                        var result;
                        while ((result = exp.exec(ret)) != null) {
                            iconlist.push('fa-' + result[1]);
                        }
                    },
                    error: function (xhr, textstatus, thrown) {
                        layer.msg('fa鍥炬爣鎺ュ彛鏈夎');
                    }
                });
                return iconlist;
            }
        };

        a.init();
        return new IconPicker();
    };

    /**
     * 閫変腑鍥炬爣
     * @param filter lay-filter
     * @param iconName 鍥炬爣鍚嶇О锛岃嚜鍔ㄨ瘑鍒玣ontClass/unicode
     */
    IconPicker.prototype.checkIcon = function (filter, iconName) {
        var el = $('*[lay-filter=' + filter + ']'),
            p = el.next().find('.layui-iconpicker-item .fa'),
            c = iconName;

        if (c.indexOf('#xe') > 0) {
            p.html(c);
        } else {
            p.html('').attr('class', 'fa ' + c);
        }
        el.attr('value', c).val(c);
    };

    var iconPicker = new IconPicker();
    exports(_MOD, iconPicker);
});