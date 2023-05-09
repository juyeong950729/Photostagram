/*
날짜 : 2023/03/03
이름 : 김진우
내용 : member js
*/
let signin_form = document.querySelector('#signin-form')
let signin_btn = document.querySelector('.btn-login')
let darkmode_toggle = document.querySelector('#darkmode-toggle')
let slide_index = 0

// input 애니메이션
document.querySelectorAll('.animate-input').forEach(e => {
    let input = e.querySelector('input')
    let button = e.querySelector('button')

    // 아이디 입력하라는 문구 위로 올리기
    input.onkeyup = () => {
        if (input.value.trim().length > 0) {
            e.classList.add('active')
        } else {
            e.classList.remove('active')
        }

        if (checkSigninInput()) {
            signin_btn.removeAttribute('disabled')
        } else {
            signin_btn.setAttribute('disabled', 'true')
        }
    }

    // 비밀번호 입력시 보이기, 숨기기 버튼 출력
    if (button) {
        button.onclick = () => {
            if (input.getAttribute('type') === 'text') {
                input.setAttribute('type', 'password')
                button.innerHTML = '비밀번호 표시'
            } else {
                input.setAttribute('type', 'text')
                button.innerHTML = '숨기기'
            }
        }
    }
})

// 6자리 이상일 때 버튼 활성화
checkSigninInput = () => {
    let inputs = signin_form.querySelectorAll('input.able')
    return Array.from(inputs).every(input => {
        return input.value.trim().length >= 6
    })
}

// 다크모드
//darkmode_toggle.onclick = (e) => {
//    e.preventDefault()
//    let body = document.querySelector('body')
//    body.classList.toggle('dark')
//    darkmode_toggle.innerHTML = body.classList.contains('dark') ? 'Lightmode' : 'Darkmode'
//}





