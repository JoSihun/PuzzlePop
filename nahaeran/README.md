# 목차

- [🗓️ 1월 15일 (월)](#%EF%B8%8F-1월-15일-월)
- [🗓️ 1월 16일 (화)](#%EF%B8%8F-1월-16일-화)
- [🗓️ 1월 17일 (수)](#%EF%B8%8F-1월-17일-수)
- [🗓️ 1월 18일 (목)](#%EF%B8%8F-1월-18일-목)
- [🗓️ 1월 19일 (금)](#%EF%B8%8F-1월-19일-금)
<br />

- [🗓️ 1월 22일 (월)](#%EF%B8%8F-1월-22일-월)
- [🗓️ 1월 23일 (화)](#%EF%B8%8F-1월-23일-화)
- [🗓️ 1월 24일 (수)](#%EF%B8%8F-1월-24일-수)
- [🗓️ 1월 25일 (목)](#%EF%B8%8F-1월-25일-목)
- [🗓️ 1월 26일 (금)](#%EF%B8%8F-1월-26일-금)
<br />

- [🗓️ 1월 29일 (월)](#%EF%B8%8F-1월-29일-월)
- [🗓️ 1월 30일 (화)](#%EF%B8%8F-1월-30일-화)
- [🗓️ 1월 31일 (수)](#%EF%B8%8F-1월-31일-수)
- [🗓️ 2월 1일 (목)](#%EF%B8%8F-2월-1일-목)
<br />

# 🗓️ 1월 15일 (월)

- (주말동안) figma 보강 + gif
  <img src="./img/240115_1.PNG" />
  <img src="./img/240115_2.gif" />

- 간단한 퍼즐 옮기기 로직 구현
  - #frontend_1/move_puzzle 브랜치 참고

# 🗓️ 1월 16일 (화)

- Jira 학습, 이슈 컨벤션 논의
- 1주차 스프린트에 이슈 등록
- branch 전략 논의
- canvas API를 이용하여 퍼즐 이동 로직 구현

# 🗓️ 1월 17일 (수)

- Jira 1주차 이슈 등록 마무리
- 코드리뷰 규칙 논의 및 결정
- Paper.js 학습
- 퍼즐 정답 판별 알고리즘 구상

# 🗓️ 1월 18일 (목)

- Paper.js 학습 2..
- Paper.js를 이용하여 피스 모양으로 자르는 기능 구현
  - 현재는 front에서 랜덤으로 피스모양(들어가고 나온 모양) 결정, 차후에 API 붙일때 해당함수 조정해야함

# 🗓️ 1월 19일 (금)

- 잘라진 피스 이동 기능 구현
- 피스 맞춰질 때 맞는 피스면 이어지는 (그룹화) 기능 구현
<br />
<hr />
<br />

# 🗓️ 1월 22일 (월)

- 2주차 jira 이슈 등록 및 스프린트 시작
- 피스 width가 줄어들 시 딱 붙지 않는 버그 해결
  -> 현재 피스의 width에 따라 위치 조정
- 피스 잘 붙지 않는 버그 해결 중 (특히 2개이상 붙어있을 때)

# 🗓️ 1월 23일 (화)

- 피스 잘 붙지 않는 버그 해결
  -> 그룹일때 클릭한 피스의 주위만 보는 것이 아닌 묶인 그룹의 주위를 전부 탐색
- 피스의 인덱스대로 그려지는 버그 해결
  -> 클릭한 피스(그룹)이 맨 위로 갱신되도록 수정 
- 보정값 수정 중

# 🗓️ 1월 24일 (수)

- Sub-PJT2 발표 초안 작성
- Sub-PJT2 발표 자료(PPT) 작성 1

# 🗓️ 1월 25일 (목)

- Sub-PJT2 발표 자료(PPT) 작성 2...

# 🗓️ 1월 26일 (금)

- Sub-PJT2 발표
- 피스 맞출때 피스의 안쪽에서만 (겹쳐졌을때만) 잘 맞춰지는 버그 해결
  -> 피스 바깥이라도 오차범위 안이면 맞춰질 수 있도록 해결

<br />
<hr />
<br />

# 🗓️ 1월 29일 (월)

- Nav Tabs 개발
- 방 목록 components 개발
  - board, GameCard
  - 협동, 배틀에 따라 다름
- 대기실 components 개발
  - board, PlayerCard 개발

# 🗓️ 1월 30일 (화)

- 대기실 components
  - EmptyPlayerCard, XPlayerCard 등
- webRTC APIs를 이용한 멀티플레이 기능 구현 1

# 🗓️ 1월 31일 (수)

- webRTC APIs를 이용한 멀티플레이 기능 구현 2
- openvidu를 이용한 음성채팅 구현 1

# 🗓️ 2월 1일 (목)

- openvidu를 이용한 음성채팅 구현 2
- 대기실 사진 선택, 피스 수 선택 컴포넌트
- 팀 선택 컴포넌트
- Footer 컴포넌트 개발 중
