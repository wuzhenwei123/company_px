/**
 * 
 * @param base_path 项目跟目录
 * @param picker 
 * @param callback 回调
 * @param accept 上传格式
 * @param proval 存储路径
 * @param type 上传类型(pic)
 */
function upload(base_path,picker,callback,accept,proval,type){
	if(accept == ''){
		accept = {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png',
	        mimeTypes: 'image/jpg,image/jpeg,image/png' 
	    }
	}else if(accept == 'excel'){
		accept = {
	        title: 'file',
	        extensions: 'xls',
	        mimeTypes: 'application/vnd.ms-excel' 
	    }
	}else if(accept == 'video'){
		accept = {
				title: 'Video',
				extensions: 'mp4',
				mimeTypes: 'video/mp4'
		}
	}
	var up_url = base_path+'/upload/exec?proVal='+proval+"&type="+type;
	var uploader = WebUploader.create({
	    // 选完文件后，是否自动上传。
	    auto: true,
	    // swf文件路径
	    swf:base_path+'/js/plugins/webuploader/Uploader.swf',
	    // 文件接收服务端。
	    server: up_url,
	    timeout: 0,
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#'+picker,
	    // 只允许选择图片文件。
	    accept: accept
	});
	uploader.on( 'uploadSuccess', function( file,response ) {
		callback(response);
	});

	uploader.on( 'uploadError', function( file ) {
//		tipError(file.id+",上传失败.");
	});

	uploader.on( 'uploadComplete', function( file ) {
	});
	
	uploader.on( 'uploadProgress', function( file, percentage ) {  
		var x = (percentage * 100).toFixed(0);
		if(accept.extensions == 'mp4'){
			layui.use('element', function(){
				var element = layui.element;
				element.progress('demo', x + '%');
			});
		}
	});
}