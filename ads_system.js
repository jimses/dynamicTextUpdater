/* author : Jim Connell */
function edit() {
      $("#editDiv").show();
}
// else if (auth == 2){
function wait() {
    ad_id = getURLData["ad_id"];
    camp_id = getURLData["camp_id"];
    $("#reviewDivbutton").show();

}
function review(){
  ad_id = getURLData["ad_id"];
  camp_id = getURLData["camp_id"];
  $("#reviewDiv").show();
  $("#campBlock").hide();
  $("#approvedivtableHold").show();
}

getCreativeFacors = function(subgrid_id, row_id){
    var subgridArray = new Array();
    var subgrid_table_id, pager_id;
    subgrid_table_id = subgrid_id + "_t";
    pager_id = "p_" + subgrid_table_id;
    $("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table><div id='" + pager_id + "' class='scroll'></div>");
    var camp_id = row_id;
    var dataurl = "/AdSystem/home?action=getcreative&q=3&id=" + row_id;
    // this displays the ad
    jQuery("#" + subgrid_table_id).jqGrid({
        url: dataurl,
        datatype: "json",
        colNames: ['Ad ID', 'Ad Name', 'Ad Size'],
        colModel: [{
            name: "id",
            index: "id",
            width: 20,
            key: true
        }, {
            name: "adname",
            index: "adname",
            width: 90,
            editable: true
        }, {
            name: "size",
            index: "size",
            width: 20,
            align: "center"
        }, ],
        rowNum: 20,
        pager: pager_id,
        sortname: 'id',
        viewrecords: true,
        sortorder: "asc",
        height: '10%',
        width: '900',
        multiselect: true,
        //onSelectRow: function(){reviewAd(ad_id, camp_id)},
        //subGridBeforeExpand:function(){reviewAd(row_id, camp_id)},
        subGrid: true,
        caption: "Ads in Campaign #" + row_id,
        /* onselectrow */
        onSelectRow: function(row_id) {
              $('#list10_text').jqGrid('GridUnload');
              $("#banTextholder").lightbox_me({
                    centered: true,
                    lightboxSpeed: "fast",
                    overlaySpeed: "fast",
                    overlayDisappearSpeed: "fast",
                    overlayCSS: {
                        background: 'black',
                        opacity: .6
                    }
                });
                jQuery("#list10_text").jqGrid('setGridParam',{
                url:"/AdSystem/home?action=getcreativetext&q=3&id="+row_id,
                page:1
                });
                $.getJSON("/AdSystem/home?action=getcomments&id=" + row_id, function (json){
                    var comments_data = json;
                    if (comments_data.rows[0]){
                        var last_comment=comments_data.rows[0].cell[0];
                        $(".results").empty();
                        $(".results").append(last_comment);
                    }
                });
                subGridBeforeExpand: function(subgrid_id) {
                  subgrid_table_id2 = subgrid_id + "_t";
                  subgridArray.push(subgrid_table_id2);
                  jQuery("#" + subgridArray[0]).jqGrid('setGridState', 'hidden');
                  $('.ui-jqgrid-titlebar-close').click();
                  $(rev_form).empty();
                  if (subgridArray.length > 1) {
                      subgridArray.shift();
                  }
                },
                subGridRowExpanded: function(subgrid_id, row_id) {
                  var subgrid_table_id2, pager_id;
                  subgrid_table_id2 = subgrid_id + "_t";
                  alert("subgrid_table_id2 = " + subgrid_table_id2)
                  pager_id = "p_" + subgrid_table_id2;
                  $("#" + subgrid_id).html("<table id='" + subgrid_table_id2 + "' class='scroll'></table><div id='" + pager_id + "' class='scroll'></div>");
                    var creative_id = subgrid_id;
                    var the_length = creative_id.length;
                    var last_char = creative_id.charAt(the_length - 1);
                    var visible_ad_id = last_char;
                    var ad_id = row_id;
                    reviewAd(ad_id, camp_id);
                    // this displays ad text
                    jQuery("#" + subgrid_table_id2).jqGrid({
                        url: "/AdSystem/home?action=getcreativetext&q=3&id=" + row_id + "&subgrid_id=" + visible_ad_id + "&r=" + Math.random(),
                        datatype: "json",
                        colNames: ['ID', 'Text', 'Order', 'Ad ID', 'Content Type', 'Version', 'Revision', 'Review Status', 'Enabled'],
                        colModel: [{
                                name: "id",
                                index: "id",
                                width: 10,
                                //key: true,
                                hidden: true,
                                editable: true,
                                editoptions: {
                                    size: 10
                                },
                                //hidden:true,
                                resizable: true
                            }, {
                                name: "content",
                                index: "content",
                                width: 50,
                                key: true,
                                editable: true,
                                hidden: false,
                                editoptions: {
                                    size: 100
                                },
                                resizable: true
                            }, {
                                name: "theorder",
                                index: "theorder",
                                width: 10,
                                align: "center",
                                key: true,
                                editable: true,
                                editoptions: {
                                    size: 10
                                },
                                hidden: false,
                                resizable: false
                            },
                            {
                                name: "ad_id",
                                index: "ad_id",
                                width: 15,
                                key: true,
                                editable: true,
                                editoptions: {
                                    size: 10
                                },
                                hidden: true,
                                resizable: false
                            }, {
                                name: "contentType",
                                index: "contentType",
                                width: 20,
                                key: true,
                                editable: true,
                                editoptions: {
                                    size: 10
                                },
                                hidden: true,
                                resizable: false
                            }, {
                                name: "version",
                                index: "version",
                                width: 20,
                                key: true,
                                editable: true,
                                editoptions: {
                                    size: 10
                                },
                                formatoptions: {
                                    disabled: false
                                },
                                hidden: true,
                                resizable: false
                            },
                            {
                                name: "revision",
                                index: "revision",
                                width: 15,
                                key: true,
                                editable: false,
                                editoptions: {
                                    size: 10
                                },
                                formatoptions: {
                                    disabled: true
                                },
                                hidden: true,
                                resizable: false
                            },
                            {
                                name: "reviw_status",
                                index: "review_status",
                                width: 20,
                                key: true,
                                editable: false,
                                edittype: "select",
                                editoptions: {
                                    size: 10
                                },
                                formatoptions: {
                                    disabled: true
                                },
                                hidden: false,
                                resizable: false
                            },

                            {
                                name: "enabled",
                                index: "enabled",
                                width: 8,
                                key: true,
                                editable: true,
                                // formatter:'checkbox',
                                editoptions: {
                                    size: 10
                                },
                                formatoptions: {
                                    disabled: true
                                },
                                hidden: true,
                                resizable: false
                            },

                        ],
                        rowNum: 10,
                        rowList: [10, 20, 30],
                        pager: pager_id,
                        sortorder: "asc",
                        sortname: 'id',
                        height: '100%',
                        width: '800',
                        editurl: '$(this)',
                        viewrecords: true,
                        sortorder: "asc",
                        multiselect: false,
                        caption: "Frame Text for Ad #" + row_id,
                        closeAfterSubmit: "true",
                        //onSelectRow: function(){alert($(this).subgrid_id('td').attr('title'))},
                        //onSelectRow: function(){reviewAd(ad_id, camp_id)},
                        footerrow: true,
                        userDataOnFooter: true,
                        altRows: true
                    });
                    jQuery("#" + subgrid_table_id2).jqGrid('navGrid', "#" + pager_id, {
                      refresh: true,
                      add: false,
                      del: false
                    },
                    {
                      beforeSubmit: showRequest,
                      closeAfterEdit: true,
                      reloadAfterSubmit: false
                    }, // edit options
                    {
                      height: 280,
                      reloadAfterSubmit: false
                    }, // add options
                    {
                      reloadAfterSubmit: false
                    }, // del options
                    {});
                    jQuery("#m1").click(function() {
                      jQuery("#list10").jqGrid('setGridState', 'hidden');
                      $.getJSON("/AdSystem/home?action=getcreativefactors&id=" + ad_id + "&camp_id=" + camp_id, function(json) {
                        var data = json.adname;
                        //alert("data="+data)
                        var id = json.id;
                        // alert("this id _ "+id)
                        var creativename = json.adname;
                        var creativesize = json.size;
                        var creativedir = json.dir;
                        var campaigndir = json.name;
                        var splitsize = creativesize.split("x");
                        var firsthalf = splitsize[0];
                        var lasthalf = splitsize[1];
                        var so;
                        so = new SWFObject("http://localhost/adsystem/ads/" + campaigndir + "/" + creativedir + "/" + creativesize + "/SWFs/" + creativename + ".swf?r=" + Math.random(), "theFlash", firsthalf, lasthalf, "10", "");
                        so.addVariable("explorerNoCache", Math.random());
                        so.addParam("wMode", "window");
                        so.write("flashcontent");
                    });
                  });
                  jQuery("#m02").click(function() {
                      window.open("http://localhost:8080/AdSystem/ads/get_creative_NW.html?id=" + ad_id + "&camp_id=" + camp_id + "&r=" + Math.random());
                  });
                  jQuery("#m03").click(function() {
                      auth = 3;
                      reviewAd(ad_id, camp_id, auth);
                      var last_ad_id = ad_id;
                      $.getJSON("/AdSystem/home?action=getcreativefactors&id=" + ad_id + "&camp_id=" + camp_id, function(json) {
                      var data = json.adname;
                      var id = json.id;
                      var creativename = json.adname;
                      var creativesize = json.size;
                      var creativedir = json.dir;
                      var campaigndir = json.name;
                      var splitsize = creativesize.split("x");
                      var firsthalf = splitsize[0];
                      var lasthalf = splitsize[1];
                      var so;
                      so = new SWFObject("http://localhost/adsystem/ads/" + campaigndir + "/" + creativedir + "/" + creativesize + "/SWFs/" + creativename + ".swf?r=" + Math.random(), "theFlash", firsthalf, lasthalf, "10", "");
                      so.addVariable("explorerNoCache", Math.random());
                      so.addParam("wMode", "window");
                      so.write("flashcontent");
                      $.getJSON("/AdSystem/home?action=getcreativefactors&id=" + ad_id + "&camp_id=" + camp_id, function(json) {
                        var so2;
                        so2 = new SWFObject("http://localhost/adsystem/ads/" + campaigndir + "/" + creativedir + "/" + creativesize + "/SWFs/" + creativename + ".swf", "theFlash", firsthalf, lasthalf, "10", "");
                        so2.addVariable("jsonURL", "http://localhost:8080/AdSystem/home?action=getadcreative_last%26id=" + ad_id + "&camp_id=" + camp_id);
                        so2.addVariable("explorerNoCache", Math.random());
                        so2.addParam("wMode", "window");
                        so2.write("flashcontent_live");
                      });
                });
                $.getJSON("/AdSystem/home?action=getcomments&id=" + ad_id, function(json) {
                    var comments_data = json;
                    if (comments_data.rows[0]) {
                        var last_comment = comments_data.rows[0].cell[0];
                        //alert("last comment ; "+last_comment);
                        $(".results").empty();
                        $(".results").append(last_comment);
                    }
                });
            });
            builtURL = "http://localhost:8080/AdSystem/jqdata/?auth=2&ad_id=1&camp_id=1";
            jQuery("#m5").click(function() {
                jQuery("#list14").jqGrid('setGridState', 'hidden');
                $.ajax({
                    type: "GET",
                    async: false,
                    url: "/AdSystem/home?action=sendforreview&demo_ad_id=" + ad_id + "&demo_camp_id=" + camp_id + "&to=" + pm + "&from=" + marketing + "&subject=AdsystemDemo&body_html=" + builtURL + "&body_txt=" + builtURL,
                    success: "close"
                });
                return false;
          });
        }

    });
    //var camp_id = last_camp_select;
  }
});
//* reveiw ad *//
function reviewAd(ad_id, camp_id, auth) {
  subgridArray.push(subgrid_table_id2);
  jQuery("#"+subgridArray[0]).jqGrid('setGridState','hidden');
      if (subgridArray.length > 1){
        subgridArray.shift();
        }
  $(".bottomdiv").show();
  $("#campBlock").hide();
  $("#banTextholder").show();
  ($("#approvedivtableHold").find('h2').append(ad_id));
  // this is for ad review approval
  $.getJSON("/AdSystem/home?action=getcreativetext&q=3&id=" + ad_id, function json(json) {
    var data = json;
    var row_length = data.rows.length;
    i = 0;
    var iAdArray = new Array("adid=" + ad_id);
    while (i < row_length) {
        var iLineArray = new Array(data.rows[i].cell[0], data.rows[i].cell[7]);
        var record_length = data.rows[i].cell.length;
        ii = 0;
        while (ii < record_length) {
            review_state = data.rows[i].cell[7];
            review_id = data.rows[i].cell[0];
            review_order = data.rows[i].cell[2];
            if (review_state == 'Approved') {
                checkit = "checked";
              } else {
                checkit = ""
            }
            approv_var = '<!--<input type="hidden" value="' + review_id + '"><input type="radio" id="' + review_id + '" class="first" name="' + review_order + '" value="1">--><input type="radio" id="' + review_id + '" class="first" name="' + review_order + '" value="1">';
            reject_var = '<!--<input type="hidden" value="' + review_id + '">--><input type="radio" id="' + review_id + '" class="second" name="' + review_order + '" value="2">';
            // if enabled for review
            if ((data.rows[i].cell[ii] == data.rows[i].cell[1]) && (data.rows[i].cell[8] == '1')) {
                var approv_option_val = "1";
                if (data.rows[i].cell[7] == 'Approved') {
                    approv_option_val = "1";
                    approve_txt = "Current"
                    approv_var = '';
                    reject_var = '';
                } else if (data.rows[i].cell[7] == 'Proposed') {
                    approv_option_val = "0";
                    approve_txt = "Proposed"
                } else if (data.rows[i].cell[7] == 'rejected') {
                    approv_option_val = "2";
                    reject_var = '';
                    approve_txt = "Rejected"
                } else if (data.rows[i].cell[7] == 'replaced') {
                    approv_option_val = "3";
                    approve_txt = "Replaced"
                }
                if (approv_option_val == "1") {
                    review_action1 = "";
                    review_action2 = "";
                }
                if (approv_option_val == "2") {
                    review_action1 = "Undo Reject";
                    review_action2 = "";
                }
                if (approv_option_val == "0") {
                    review_action1 = "Approve";
                    review_action2 = "Reject";
                }
                var rev_form = jQuery('#rev_form').append('<tr width=700 style="padding:10px;"><td role=gridcell with=30% id="record_content" style="padding:10px">' + data.rows[i].cell[1] + '</td><td with=10% id="record_id" style="padding:10px;">' + data.rows[i].cell[0] + '</td><td with=10% id="record_order" style="padding:10px;">' + data.rows[i].cell[2] + '</td><td with=10% id="record_review_status" style="padding:10px">' + approve_txt + '</td><td with=10% style="padding:10px">' + approv_var + '</td><td with=10% class="record_review_status" style="padding:10px">' + review_action1 + '</td><td with=10% style="padding:10px">' + reject_var + '</td><td with=10% class="record_review_status" style="padding:10px">' + review_action2 + '</td></tr>');
                var iAdString = iAdArray.toString();
                //$('#results').append(iAdString);
                var queryString = $.param(iAdString);
            }
            ii++;
          }
        i++;
      }/*end while */
      jQuery('#rev_form').append('<tr><td colspan=8><input type="hidden" name="ad_id" value="' + ad_id + '"><textarea name="comments" id="comments" cols="30" rows="3">Comments</textarea>&nbsp;&nbsp;<input type=button id="m4" class="mainComparebtns" value="Save & Publish"></td></tr>');
      radioArray = new Array();
      $("input:radio").each(function() {
        var update_box = ($(this).attr('class'));
        var update_action = ($(this).attr('name'));
        var update_id = $(this).val();
        var update_text_id = ($(this).attr('id'));
        $(this).click(function() {
            var update_box = ($(this).attr('class'));
            var update_action = ($(this).attr('name'));
            var update_text_id = ($(this).attr('id'));
            var newValue = ("update_text_id=" + update_text_id + "&update_action=" + update_action + "&updatebox=" + update_box);
            radioArray.push(newValue);
            if (update_box == "first") {
                update_txt = " Approved";
            } else {
                update_txt = " Rejected";
            }
            ($(this).parent().parent().css('background-color', 'white'));
            $(this).parent().parent().find('#record_review_status').replaceWith(update_txt).css('color', 'red');
            $.getJSON("/AdSystem/home?action=updateReviewStatus&update_action=" + update_action + "&update_box=" + update_box + "&update_text_id=" + update_text_id);
            $('#rev_form').append(rev_form);
        });
      });
      // this is the review response button
      jQuery("#m4").click(function() {
        var values = $('#rev_form').serialize();
        d_values = values.indexOf('&');
        $(rev_form).ajaxSubmit({
            data: values,
            type: "GET",
            async: false,
            target: '#results',
            beforeSubmit: showResponseRequest,
            //url : "/AdSystem/home?action=uupdateadtext&values=" + values
            url: '/AdSystem/home?action=updateadtext&values=' + values
        });
      });
    });
    $.getJSON("/AdSystem/home?action=getcomments&id=" + ad_id, function(json) {
    var comments_data = json;
    if (comments_data.rows[0]) {
        var last_comment = comments_data.rows[0].cell[0];
        $("#results").append(last_comment);
    }
  });
  jQuery("#m1").click(function() {
    jQuery("#list10").jqGrid('setGridState', 'hidden');
    $.getJSON("/AdSystem/home?action=getcreativefactors&id=" + ad_id + "&camp_id=" + camp_id, function(json) {
        var data = json.adname;
        var id = json.id;
        var creativename = json.adname;
        var creativesize = json.size;
        var creativedir = json.dir;
        var campaigndir = json.name;
        var splitsize = creativesize.split("x");
        var firsthalf = splitsize[0];
        var lasthalf = splitsize[1];
        var so;
        so = new SWFObject("http://localhost/adsystem/ads/" + campaigndir + "/" + creativedir + "/" + creativesize + "/SWFs/" + creativename + ".swf?r=" + Math.random(), "theFlash", firsthalf, lasthalf, "10", "");
        so.addVariable("explorerNoCache", Math.random());
        so.addParam("wMode", "window");
        so.write("flashcontent");
    });
  });
});
jQuery("#m2").click(function() {
  window.open("http://localhost:8080/AdSystem/ads/get_creative.html?id=" + ad_id + "&camp_id=" + camp_id + "&r=" + Math.random());
});
$(document).ready(function() {
    auth = 1;
    var getURLData = new Array();
    authstring = window.location.search;
    if (authstring){
      authstring = authstring.substr(1);
      var pairs = authstring.split("&");
      for (var i = 0; i < pairs.length; i++){
        var nv = pairs[i].split("=");
        var s = nv[0];
        var v = nv[1];
        getURLData[s] = v;
      }
    }
    auth = getURLData["auth"];
    if (!auth){
      auth=1;
    }
    var ad_id;
    var camp_id;
    var pm = "admin@thewebdevelopmentco.com";
    var marketing = "marketer@tthewebdevelomentco.com";
    $("#editDiv").hide();
    $("#reviewDivbutton").hide();
    $("#reviewDiv").hide();
    $("#approvedivtableHold").hide();
    jQuery(".cbox").live('click', function() {
        var id = jQuery("#list10_d").jqGrid('getGridParam', 'selrow');
        if (id) {
            var ret = jQuery("#list10_d").jqGrid('getRowData', id);
            alert("id=" + ret.id + "retadmane =" + ret.adname);
        } else {
            alert("Please select row");
        }
    });
    $(".cbox").click(function() {
        var gr = jQuery("#list10_d").jqGrid('getGridParam', 'selrow');
        if (gr != null) jQuery("#list10_d").jqGrid('editGridRow', gr, {
            height: 280,
            reloadAfterSubmit: false
        });
    });
    jQuery(".approve").live('click', function() {
        $("#reviewDiv").lightbox_me({
            centered: true,
            lightboxSpeed: "fast",
            overlaySpeed: "fast",
            overlayDisappearSpeed: "fast",
            overlayCSS: {
                background: 'black',
                opacity: .6
            }
        });
    });
    $('.approveTextarea').each(function() {
        var default_value = this.value;
        $(this).focus(function() {
            if (this.value == default_value) {
                this.value = '';
            }
        });
        $(this).blur(function() {
            if (this.value == '') {
                this.value = default_value;
            }
        });
    })
    jQuery("#list14").jqGrid({
        url: "/AdSystem/home?action=getcampaigns",
        datatype: "json",
        colNames: ['ID', 'Name', 'Start'],
        colModel: [{
            name: "id",
            index: "id",
            width: 10,
            key: true
        }, {
            name: "name",
            index: "name",
            width: 110,
            editable: true
        }, {
            name: "Start",
            index: "Start",
            width: 50,
            align: "center"
        }, ],
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: '#pager10',
        sortname: 'id',
        height: '100%',
        width: '1000',
        viewrecords: true,
        sortorder: "desc",
        multiselect: false,
        subGrid: true,
        caption: "Campaigns",
        subGridRowExpanded: function(){ getCreativeFactors(subgrid_id, row_id)});
        // this click shows the current and proposed creatives sid-by-side at the bottom of the page
        jQuery("#m3").click(function() {
            // jQuery("#list14").jqGrid('setGridState','hidden');
            $("#adCompare").lightbox_me({
                centered: false,
                lightboxSpeed: "fast",
                overlaySpeed: "fast",
                overlayDisappearSpeed: "fast",
                overlayCSS: {
                    background: 'black',
                    opacity: .6
                }
            });
            //$("#proposed").show();
            // $("#approved").show();
            $.getJSON("/AdSystem/home?action=getcreativefactors&id=" + ad_id + "&camp_id=" + camp_id, function(json) {
                var data = json.adname;
                var id = json.id;
                var creativename = json.adname;
                var creativesize = json.size;
                var creativedir = json.dir;
                var campaigndir = json.name;
                var splitsize = creativesize.split("x");
                var firsthalf = splitsize[0];
                var lasthalf = splitsize[1];
                var so;
                so = new SWFObject("http://localhost/adsystem/ads/" + campaigndir + "/" + creativedir + "/" + creativesize + "/SWFs/" + creativename + ".swf?r=" + Math.random(), "theFlash", firsthalf, lasthalf, "10", "");
                so.addVariable("explorerNoCache", Math.random());
                so.addParam("wMode", "window");
                so.write("flashcontent");
                $.getJSON("/AdSystem/home?action=getcreativefactors&id=" + ad_id + "&camp_id=" + camp_id, function(json) {
                    var so2;
                    so2 = new SWFObject("http://localhost/adsystem/ads/" + campaigndir + "/" + creativedir + "/" + creativesize + "/SWFs/" + creativename + ".swf", "theFlash", firsthalf, lasthalf, "10", "");
                    so2.addVariable("jsonURL", "http://localhost:8080/AdSystem/home?action=getadcreative_last%26id=" + ad_id + "&camp_id=" + camp_id);
                    so2.addVariable("explorerNoCache", Math.random());
                    so2.addParam("wMode", "window");
                    so2.write("flashcontent_live");
                });
            });
        });

        /* // this is for the review response button */
        jQuery("#m5").click(function(){
            $.ajax({
            type: "GET",
            async: false,
            url: '"/AdSystem/home?action=sendforreview&id="+ad_id+"&to="+pm+"&from="+marketing+"&subject=AdsystemDemo&body_html="+builtURL+"&body_txt="+builtURL+'
        });
        return false;
      });
      $("#adCompare").lightbox_me({
            centered: false,
            lightboxSpeed: "fast",
            overlaySpeed: "fast",
            overlayDisappearSpeed: "fast",
            overlayCSS: {
                background: 'black',
                opacity: .6
            }
        });
        //$("#proposed").show();
        //$("#approved").show();
        //jQuery("#list10").jqGrid('setGridState','hidden');
        $.getJSON("/AdSystem/home?action=getcreativefactors&id=" + ad_id + "&camp_id=" + camp_id, function(json) {
            var data = json.adname;
            var id = json.id;
            var creativename = json.adname;
            var creativesize = json.size;
            var creativedir = json.dir;
            var campaigndir = json.name;
            var splitsize = creativesize.split("x");
            var firsthalf = splitsize[0];
            var lasthalf = splitsize[1];
            var so;
            so = new SWFObject("http://localhost/adsystem/ads/" + campaigndir + "/" + creativedir + "/" + creativesize + "/SWFs/" + creativename + ".swf?r=" + Math.random(), "theFlash", firsthalf, lasthalf, "10", "");
            so.addVariable("explorerNoCache", Math.random());
            so.addParam("wMode", "window");
            so.write("flashcontent");
            $.getJSON("/AdSystem/home?action=getcreativefactors&id=" + ad_id + "&camp_id=" + camp_id, function(json) {
                var so2;
                so2 = new SWFObject("http://localhost/adsystem/ads/" + campaigndir + "/" + creativedir + "/" + creativesize + "/SWFs/" + creativename + ".swf", "theFlash", firsthalf, lasthalf, "10", "");
                so2.addVariable("jsonURL", "http://localhost:8080/AdSystem/home?action=getadcreative_last%26id=" + ad_id + "&camp_id=" + camp_id);
                so2.addVariable("explorerNoCache", Math.random());
                so2.addParam("wMode", "window");
                so2.write("flashcontent_live");
            });
        });
    }
    // converting post data to querystring for form edit submit
    function showRequest(formData) {
        var queryString = $.param(formData);
        var sGet = queryString;
        if (sGet) {
            var sNVPairs = sGet.split("&");
            for (var i = 0; i < sNVPairs.length; i++) {
                var sNV = sNVPairs[i].split("=");
            }
            // content id
            var sn1 = sNVPairs[0].split("=");
            var fValue = sn1[1];
            // content
            var sn2 = sNVPairs[1].split("=");
            var sValue = sn2[1];
            // text order
            var sn3 = sNVPairs[2].split("=");
            var tValue = sn3[1];
            // ad id
            var sn4 = sNVPairs[3].split("=");
            var foValue = sn4[1];
            // content type
            var sn5 = sNVPairs[4].split("=");
            var fiValue = sn5[1];
            // version
            var sn6 = sNVPairs[5].split("=");
            var siValue = sn6[1];
            // enabled
            var sn7 = sNVPairs[6].split("=");
            var seValue = sn7[1];
            // sub grid id
            var sn8 = sNVPairs[7].split("=");
            var eiValue = sn8[0];
        }
        $.ajax({
            type: "GET",
            async: false,
            url: "/AdSystem/home?action=updatefromadtext&q=3&id=" + fValue + "&ad_id=" + foValue + "&content=" + sValue + "&thorder=" + tValue + "&version=" + siValue + "&contentType=" + fiValue + "&r=" + Math.random(),
            //target: "/AdSystem/home?action=getcreativetext&q=3&id=" + ids2 + "&subgrid_id=" + visible_ad_id + "&r=" +Math.random(),
            success: function() {
                jQuery("#" + eiValue + "_t").trigger('reloadGrid');
                reviewAd(foValue);
                $('.ui-jqdialog-titlebar-close').click();
                $("#adTextholder").lightbox_me({
                    centered: true,
                    lightboxSpeed: "fast",
                    overlaySpeed: "fast",
                    overlayDisappearSpeed: "fast",
                    overlayCSS: {
                        background: 'black',
                        opacity: .6
                    }
                });
                jQuery("#list10_text").jqGrid('setGridParam',{
                    url:"/AdSystem/home?action=getcreativetext&q=3&id="+row_id,
                    page:1
                });
        $('.ui-jqdialog-titlebar-close').click();

            }
        });
        return false;
      }
}); // close document ready
