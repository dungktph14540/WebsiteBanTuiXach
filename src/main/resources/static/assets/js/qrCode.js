		let video;
		let scanner;
	
		function openCamera() {
			video = document.getElementById('video');
			scanner = new Instascan.Scanner({ video: video });
	
			scanner.addListener('scan', function (content) {
				// Handle the scanned QR code content here
				// In this example, we're logging it to the console
				console.log('Scanned QR code content:', content);
	
				// Redirect to the scanned URL (adjust this part based on your needs)
				window.location.href = content;
			});
	
			Instascan.Camera.getCameras().then(function (cameras) {
				if (cameras.length > 0) {
					scanner.start(cameras[0]);
				} else {
					console.error('No cameras found.');
					alert('No cameras found.');
				}
			}).catch(function (e) {
				console.error(e);
			});
		}
	
		function closeCamera() {
			if (scanner) {
				scanner.stop();
			}
			if (video.srcObject) {
				const tracks = video.srcObject.getTracks();
				tracks.forEach(track => track.stop());
				video.srcObject = null;
			}
		}
	
		function toggleFullscreen() {
			if (video.requestFullscreen) {
				video.requestFullscreen();
			} else if (video.mozRequestFullScreen) {
				video.mozRequestFullScreen();
			} else if (video.webkitRequestFullscreen) {
				video.webkitRequestFullscreen();
			} else if (video.msRequestFullscreen) {
				video.msRequestFullscreen();
			}
		}
	
		// Bắt sự kiện khi modal được ẩn
		$('#cameraModal').on('hidden.bs.modal', function () {
			closeCamera();
		});
