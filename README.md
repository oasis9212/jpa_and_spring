# jpa_and_spring

OSIV 관련 
콘솔 로그에서 이런게 뜨는 것이 있다.
JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning

db 커넥션의 시작 지점 부터 영속성 컨텍스트와 db 커넥션을 유지한다. 
db 다시 전송 시점: 유저에게 완전히 응답값을 보내줄때 까지 .
  lazyload 등 프록시 객체를 일단 가지고 있어야하기 때문에 영속성 컨텍스트가 사라지지않는다.
만일 insert 넣고 난뒤에 응답값을 유저에게 보내줄때 그때 사라진다.  


단점: 오랜시간 동안 db을 물고 있기 때문에 실시간 트래픽을 못가져올수 있다. 
예) 컨트롤러에서 외부 API 호출하면 외부 API대기 시간 만큼 커넥션 리소스를 반환 하지 못하고 유지해야한다. 



OSIV OFF 

시점: 트렌젝션을 돌리고 끝난 뒤에 영속성 컨텍스트를 바로 종료 시키는 방법. 

장점: 트래픽이 많을때 빠르게 처리. 
단점: lazy 로딩을 컨트롤러 view에서 처리를 못한다. 트랜젝션을 강제적으로 호출. 
     따라서 서비스단에서 전부 처리해야한다. 

따라서 고객서비스 실시간 API는 OSIV를 끄고 ADMIN 같은 아니면 트래픽이 적은데는 OSIV를 킨다. 