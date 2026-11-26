# 연습문제

백준과 프로그래머스에서 푼 코딩테스트 문제의 Java 풀이 모음입니다.

**풀이 지도** → https://park-jong.github.io/codeTest/

언제·어떤 문제를·어떤 기법으로 풀었는지 한눈에 보는 사이트입니다. 풀이 파일과 커밋 기록을 분석해 자동으로 만들어집니다.

## 폴더 구조

```
untitled/src/      풀이 코드 (IntelliJ 프로젝트)
  Main.java        새 문제를 풀 때 쓰는 빈 템플릿
tools/             풀이 지도 사이트 생성기
.github/workflows/ GitHub Pages 자동 배포
```

## 파일 이름 규칙

| 플랫폼 | 형식 | 예시 |
|---|---|---|
| 백준 | `Main_문제번호_제목.java` | `Main_14434_놀이기구1.java` |
| 백준 (태그 포함) | `Main_문제번호_태그_제목.java` | `Main_2133_dp_타일채우기.java` |
| 프로그래머스 | `Solution_제목.java` | `Solution_블록이동하기.java` |

번호와 제목 사이에 넣은 단어는 알고리즘 태그로 인식됩니다. 태그는 여러 개 붙일 수 있습니다. 예: `Main_2031_DP_BinarySearch_이쿠키달지않아.java`

인식하는 태그는 `dp`, `greedy`, `bfs`, `dfs`, `graph`, `tree`, `binarysearch`, `twopointer`, `kmp`, `adhoc`, `bitmasking`, `minimax`, `브루트포스`, `다익스트라`, `플로이드워셜`, `누적합`, `최대유량`, `백트래킹`, `구현` 등입니다. 커밋 메시지에 쓴 단어도 같은 방식으로 인식합니다.

## 풀이 지도

- **대시보드**: 푼 문제 수, 날짜별 풀이 히트맵, 월별 풀이 수, 자주 쓴 기법
- **문제 목록**: 번호·제목·기법으로 검색하고 플랫폼과 기법으로 필터링
- **문제 상세**: 사용한 기법과 근거가 된 코드 줄, 메서드 호출 구조도, 전역 필드, 하이라이트된 코드

기법은 두 가지 방법으로 분류합니다.

1. **직접 붙인 태그**: 파일 이름과 커밋 메시지에서 읽습니다.
2. **자동 감지**: `PriorityQueue`, `mid = (l + r) / 2`, `dp[` 같은 코드 패턴을 찾습니다. 변수 이름에 의존하는 규칙이 많아 틀릴 수 있으니, 정확히 분류하고 싶은 문제는 파일 이름에 태그를 붙이는 것이 가장 확실합니다.

풀이 날짜는 각 파일이 처음 커밋된 날을 기준으로 합니다.

### 로컬에서 보기

[Node.js](https://nodejs.org) 18 이상이 필요합니다. 외부 패키지는 쓰지 않습니다.

```bash
node tools/build-site.mjs
```

`site/index.html`이 만들어지고, 브라우저로 바로 열 수 있습니다. 날짜가 git 기록에서 오기 때문에 새 풀이는 **커밋한 뒤에** 실행해야 날짜가 제대로 잡힙니다. `site/`는 생성물이라 git에 올리지 않습니다.

### GitHub Pages 배포

`main`에 풀이를 푸시하면 GitHub Actions(`.github/workflows/pages.yml`)가 사이트를 빌드해서 배포합니다. Actions 탭에서 수동으로 실행할 수도 있습니다.

처음 한 번은 설정이 필요합니다.

1. 저장소 **Settings → Pages**로 이동
2. **Build and deployment → Source**를 **GitHub Actions**로 변경
3. Actions 탭에서 "풀이 지도 배포" 워크플로를 실행하거나 새로 푸시

무료 계정에서 GitHub Pages를 쓰려면 저장소가 공개(public)여야 합니다. 사이트에는 풀이 코드 전체가 들어갑니다.
