var Script = function () {

  // begin first table
  $('#sample_1').dataTable({
    "sDom": "<'row'<'col-sm-3'l><'col-sm-4'f><'#addP.col-sm-3'><'#addQ.col-sm-2'>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
    "sPaginationType": "bootstrap",
    "oLanguage": {
      "sLengthMenu": "_MENU_ 条每页",
      "oPaginate": {
        "sPrevious": "上一页",
        "sNext": "下一页"
      },
      "sInfo": "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
      "sInfoEmpty": "记录数为0",
      "sSearch": "查 找："
    },

    "aoColumnDefs": [{
      'bSortable': false,
      'aTargets': [0]
    }]
  });

  jQuery('#sample_1 .group-checkable').change(function () {
    var set = jQuery(this).attr("data-set");
    var checked = jQuery(this).is(":checked");
    jQuery(set).each(function () {
      if (checked) {
        $(this).attr("checked", true);
      } else {
        $(this).attr("checked", false);
      }
    });
    jQuery.uniform.update(set);
  });

  jQuery('#sample_1_wrapper .dataTables_filter input').addClass("form-control"); // modify table search input
  jQuery('#sample_1_wrapper .dataTables_length select').addClass(
      "form-control"); // modify table per page dropdown
  jQuery('#addQ').append(
      '<div class="dataTables_filter" id="sample_1_filter"><button id="newQuestion" class="btn btn-info">新增问题</button></div>') //添加子节点
  jQuery('#addP').append(
      '<div class="dataTables_filter" id="sample_1_filter"><button id="addToPaper" class="btn btn-info">添加到试卷</button></div>') //添加子节点
  // begin second table
  $('#sample_2').dataTable({
    "sDom": "<'row'<'col-sm-4'l><'col-sm-4'f><'#btn-add.col-sm-4'>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
    "sPaginationType": "bootstrap",
    "oLanguage": {
      "sLengthMenu": "_MENU_ 条每页",
      "oPaginate": {
        "sPrevious": "上一页",
        "sNext": "下一页"
      },
      "sInfo": "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
      "sInfoEmpty": "记录数为0",
      "sSearch": "查 找："
    },
    "aoColumnDefs": [{
      'bSortable': false,
      'aTargets': [0]
    }]
  });

  jQuery('#sample_2 .group-checkable').change(function () {
    var set = jQuery(this).attr("data-set");
    var checked = jQuery(this).is(":checked");
    jQuery(set).each(function () {
      if (checked) {
        $(this).attr("checked", true);
      } else {
        $(this).attr("checked", false);
      }
    });
    jQuery.uniform.update(set);
  });

  jQuery('#sample_2_wrapper .dataTables_filter input').addClass("form-control"); // modify table search input
  jQuery('#sample_2_wrapper .dataTables_length select').addClass(
      "form-control"); // modify table per page dropdown
  jQuery('#btn-add').append(
      '<div class="dataTables_filter" id="sample_1_filter"><button id="addPaper" class="btn btn-info">新增</button></div>');
  // begin: third table
  $('#sample_3').dataTable({
    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
    "sPaginationType": "bootstrap",
    "oLanguage": {
      "sLengthMenu": "_MENU_ per page",
      "oPaginate": {
        "sPrevious": "Prev",
        "sNext": "Next"
      }
    },
    "aoColumnDefs": [{
      'bSortable': false,
      'aTargets': [0]
    }]
  });

  jQuery('#sample_3 .group-checkable').change(function () {
    var set = jQuery(this).attr("data-set");
    var checked = jQuery(this).is(":checked");
    jQuery(set).each(function () {
      if (checked) {
        $(this).attr("checked", true);
      } else {
        $(this).attr("checked", false);
      }
    });
    jQuery.uniform.update(set);
  });

  jQuery('#sample_3_wrapper .dataTables_filter input').addClass("form-control"); // modify table search input
  jQuery('#sample_3_wrapper .dataTables_length select').addClass(
      "form-control"); // modify table per page dropdown
  //begin result-list
  $('#result_list').dataTable({
    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
    "sPaginationType": "bootstrap",
    "oLanguage": {
      "sLengthMenu": "_MENU_ 条每页",
      "oPaginate": {
        "sPrevious": "上一页",
        "sNext": "下一页"
      },
      "sInfo": "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
      "sInfoEmpty": "记录数为0",
      "sSearch": "查 找："
    },

    "aoColumnDefs": [{
      'bSortable': false,
      'aTargets': [0]
    }]
  });

  jQuery('#result_list_wrapper .dataTables_filter input').addClass(
      "form-control"); // modify table search input
  jQuery('#result_list_wrapper .dataTables_length select').addClass(
      "form-control"); // modify table per page dropdown
}();