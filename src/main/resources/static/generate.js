// generate.js

// ⚠️ 누락된 reviewModal 관련 변수 및 함수 선언 추가
const reviewModal = document.getElementById('reviewModal');
const closeReviewBtn = document.getElementById('closeReviewModal');
const generatedProblemList = document.getElementById('generatedProblemList');
const cancelBtn = document.getElementById('cancelGeneration');
const saveBtn = document.getElementById('saveProblems');
const modal = document.getElementById('generationModal'); // 설정 모달
const openBtn = document.getElementById('openModalButton');
const closeBtn = document.querySelector('.close-button'); // 설정 모달 닫기 버튼
const form = document.getElementById('problemGenerationForm');
const submitButton = document.getElementById('submitButton');
const submitText = document.getElementById('submitText');
const loadingSpinner = document.getElementById('loadingSpinner');

let problems = [];


// ⚠️ 단원 목록 데이터 정의 (문제 생성 모달과 동일)
const UNIT_DATA = [
    { id: 'unit-1', name: '몇 백의 덧셈과 뺄셈' },
    { id: 'unit-2', name: '받아올림이 있는 한 자리 수의 덧셈' },
    { id: 'unit-3', name: '곱셈의 기초' }
];

const unitSidebarList = document.getElementById('unitSidebarList');
const contentTitle = document.getElementById('contentTitle');
const problemListContainer = document.getElementById('problemListContainer');

// --- 1. 사이드바 렌더링 및 이벤트 추가 함수 ---
function renderUnitSidebar() {
    unitSidebarList.innerHTML = ''; // 기존 내용 초기화

    UNIT_DATA.forEach(unit => {
        const link = document.createElement('a');
        link.href = '#';
        link.classList.add('unit-item');
        link.dataset.unitId = unit.id; // 단원 ID 저장
        link.textContent = unit.name;

        link.addEventListener('click', (e) => {
            e.preventDefault();
            handleUnitClick(unit.id, unit.name);
        });

        unitSidebarList.appendChild(link);
    });
}

function toggleLoading(isLoading) {
    if (isLoading) {
        submitButton.disabled = true; // 버튼 비활성화
        submitButton.classList.add('loading');
        submitText.textContent = '생성 중...'; // 텍스트 변경
    } else {
        submitButton.disabled = false; // 버튼 활성화
        submitButton.classList.remove('loading');
        submitText.textContent = '문제 생성'; // 텍스트 복구
    }
}

// --- 2. 단원 클릭 시 동작 함수 ---
function handleUnitClick(unitId, unitName) {
    // 사이드바 active 클래스 관리
    document.querySelectorAll('.unit-item').forEach(item => {
        item.classList.remove('active');
    });
    document.querySelector(`.unit-item[data-unit-id="${unitId}"]`).classList.add('active');

    // 메인 콘텐츠 제목 변경
    contentTitle.textContent = `${unitName} 문제 목록`;

    // 더미 메시지를 출력합니다.
    problemListContainer.innerHTML = `
          <p class="placeholder-text">"${unitName}" 단원으로 생성된 문제가 표시될 영역입니다.</p>
          <p class="placeholder-text-small">생성하기 버튼을 눌러 문제를 만들어보세요.</p>
      `;

    console.log(`단원 클릭됨: ${unitName} (${unitId})`);
}

// --- 3. 문제 목록 렌더링 함수 (이전에 정의됨) ---
function renderProblems(problems) {
    generatedProblemList.innerHTML = '';

    if (problems.length === 0) {
        generatedProblemList.innerHTML = '<p class="no-problems">생성된 유효한 문제가 없습니다.</p>';
        return;
    }

    problems.forEach((p, index) => {
        let formattedScript = p.script;
        if (p.variables) {
            for (const key in p.variables) {
                const regex = new RegExp(`{${key}}`, 'g');
                formattedScript = formattedScript.replace(regex, `{${p.variables[key]}}`);
            }
        }

        const problemDiv = document.createElement('div');
        problemDiv.className = 'generated-problem-item';
        problemDiv.innerHTML = `
            <div class="problem-header">
                <strong>${index + 1}. ${p.unit} (${p.level})</strong>
                <span class="problem-type">${p.type}</span>
            </div>
            <p class="problem-script">${formattedScript}</p>
            <div class="problem-answer">정답: ${p.answer}</div>
        `;
        generatedProblemList.appendChild(problemDiv);
    });
}


// --- 4. POST 요청 및 문제 출력 함수 ---
async function postAndRenderProblems(formData) {
    const url = '/generate';

    const requestBody = {
        unit: formData.unit,
        type: formData.type,
        difficulty: '상',
        count: parseInt(formData.count)
    };

    toggleLoading(true);

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        });

        if (!response.ok) {
            throw new Error(`서버 에러: ${response.statusText}`);
        }

        const data = await response.json();
        problems = data.problems;

        // 문제 렌더링 및 모달 전환
        renderProblems(data.problems);
        modal.style.display = 'none'; // 설정 모달 닫기
        reviewModal.style.display = 'block'; // 검토 모달 열기

    } catch (error) {
        console.error("Fetch Error:", error);
        alert('문제 생성 및 요청에 실패했습니다: ' + error.message);
    } finally {
        toggleLoading(false);
    }
}

// --- 5. 이벤트 핸들러 설정 ---

// 페이지 로드 시 사이드바 렌더링
renderUnitSidebar();

// 설정 모달 열기
openBtn.onclick = function() {
    modal.style.display = 'block';
}

// 설정 모달 닫기 (X 버튼)
closeBtn.onclick = function() {
    modal.style.display = 'none';
}

// 검토 모달 닫기 (X 버튼)
closeReviewBtn.onclick = function() {
    if (confirm('정말로 생성을 취소하시겠습니까? 저장되지 않은 문제는 사라집니다.')) {
        reviewModal.style.display = 'none';
    }
}

// 검토 모달 취소 버튼
cancelBtn.onclick = function() {
    if (confirm('정말로 생성을 취소하시겠습니까? 저장되지 않은 문제는 사라집니다.')) {
        reviewModal.style.display = 'none';
    }
}

// 저장 버튼 (동작 없음)
saveBtn.onclick = async function () {
    try {
        const response = await fetch('/problem', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(problems)
        });

        if (!response.ok) {
            throw new Error(`서버 에러: ${response.statusText}`);
        }

        alert('문제 저장 성공!');
    } catch (error) {
        console.error('저장 중 오류:', error);
        alert('문제 저장 중 오류가 발생했습니다: ' + error.message);
    }
}

// 폼 제출 시
form.onsubmit = function(event) {
    event.preventDefault();

    const unit = document.getElementById('unit').value;
    const type = document.getElementById('type').value;
    const count = document.getElementById('count').value;

    const formData = { unit, type, count, difficulty: '상' };

    console.log("전송될 데이터:", formData);

        // POST 요청 및 문제 렌더링 시작
    postAndRenderProblems(formData);
}

// 모달 바깥 영역 클릭 시 모달 닫기
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = 'none';
    } else if (event.target == reviewModal) {
        // 검토 모달의 경우, 실수로 닫는 것을 방지하기 위해 confirm 추가
        if (confirm('정말로 생성을 취소하시겠습니까? 저장되지 않은 문제는 사라집니다.')) {
            reviewModal.style.display = 'none';
        }
    }
}