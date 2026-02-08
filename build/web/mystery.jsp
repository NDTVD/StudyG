<%@ page session="true" %>
<%
    String user = (String) session.getAttribute("user");
    if (user == null) { response.sendRedirect("login.jsp"); return; }
    Integer plays = (Integer) session.getAttribute("plays"); if (plays==null) plays=0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mystery Boxes</title>
    <link rel="stylesheet" href="css/style.css" />
    <style>
        .grid { display:grid; grid-template-columns: repeat(4, 1fr); gap:10px; max-width:700px; margin:20px auto; }
        .box { background:#eee; border:2px solid #ccc; height:120px; display:flex; align-items:center; justify-content:center; cursor:pointer; font-weight:bold; }
        .topbar { display:flex; justify-content:space-between; align-items:center; max-width:700px; margin:10px auto; }
    </style>
    <script>
        async function openBox(el, idx) {
            if (el.dataset.opened==='true') return;
            try {
                el.textContent = 'Opening...';
                const res = await fetch('openbox',{method:'POST', headers:{'Content-Type':'application/x-www-form-urlencoded'}, body:'index='+idx});
                if (!res.ok) { el.textContent='Error'; return; }
                const j = await res.json();
                if (j.error) { el.textContent = j.error; return; }
                // multi reveal (when plays == 0)
                    if (j.multi && Array.isArray(j.results)) {
                    const boxes = document.querySelectorAll('.box');
                    for (let i=0;i<j.results.length && i<boxes.length;i++) {
                        const b = boxes[i];
                        const r = j.results[i];
                        // skip previously picked / excluded boxes
                        if (r.reward === 'excluded') continue;
                        b.classList.remove('jackpot','sticker','play2','play1','points50','none');
                        b.classList.add('opened'); b.dataset.opened='true';
                        if (r.reward && r.reward.startsWith('play:100')) {
                            b.classList.add('jackpot'); b.textContent = 'JACKPOT (100 plays)';
                        } else if (r.reward && r.reward.startsWith('item:')) {
                            b.classList.add('sticker'); b.textContent = r.itemDisplay ? r.itemDisplay : r.reward;
                        } else if (r.reward && r.reward.startsWith('play:2')) {
                            b.classList.add('play2'); b.textContent = 'Play x2';
                        } else if (r.reward && r.reward.startsWith('play:1')) {
                            b.classList.add('play1'); b.textContent = 'Play x1';
                        } else if (r.reward && r.reward.startsWith('points:')) {
                            b.classList.add('points50'); b.textContent = '50 pts';
                        } else {
                            b.classList.add('none'); b.textContent = r.reward;
                        }
                    }
                    // preview only: do not change displayed stats
                    return;
                }
                // single reveal - apply color class and friendly text
                el.classList.remove('jackpot','sticker','play2','play1','points50','none');
                el.classList.add('opened'); el.dataset.opened='true';
                if (j.reward && j.reward.startsWith('play:100')) {
                    el.classList.add('jackpot'); el.textContent = 'JACKPOT (100 plays)';
                } else if (j.reward && j.reward.startsWith('item:')) {
                    el.classList.add('sticker'); el.textContent = j.itemDisplay ? j.itemDisplay : j.reward;
                } else if (j.reward && j.reward.startsWith('play:2')) {
                    el.classList.add('play2'); el.textContent = 'Play x2';
                } else if (j.reward && j.reward.startsWith('play:1')) {
                    el.classList.add('play1'); el.textContent = 'Play x1';
                } else if (j.reward && j.reward.startsWith('points:')) {
                    el.classList.add('points50'); el.textContent = '50 pts';
                } else {
                    el.classList.add('none'); el.textContent = j.reward;
                }
                document.getElementById('plays').textContent = j.plays;
                document.getElementById('points').textContent = j.points;
            } catch(e){ el.textContent='Err'; }
        }
    </script>
    <script>
        // auto-preview when plays == 0
        window.addEventListener('DOMContentLoaded', function() {
            try {
                var playsEl = document.getElementById('plays');
                if (playsEl && parseInt(playsEl.textContent) === 0) {
                    // trigger preview request (no index required)
                    fetch('openbox', {method:'POST', headers:{'Content-Type':'application/x-www-form-urlencoded'}, body:'index=0'})
                        .then(r => r.json())
                        .then(j => {
                            if (j && j.multi && Array.isArray(j.results)) {
                                // reuse existing multi handling by calling openBox handler logic
                                const boxes = document.querySelectorAll('.box');
                                for (let i=0;i<j.results.length && i<boxes.length;i++) {
                                    const b = boxes[i];
                                    const r = j.results[i];
                                    // skip excluded boxes
                                    if (r.reward === 'excluded') continue;
                                    b.classList.remove('jackpot','sticker','play2','play1','points50','none');
                                    b.classList.add('opened'); b.dataset.opened='true';
                                    if (r.reward && r.reward.startsWith('play:100')) {
                                        b.classList.add('jackpot'); b.textContent = 'JACKPOT (100 plays)';
                                    } else if (r.reward && r.reward.startsWith('item:')) {
                                        b.classList.add('sticker'); b.textContent = r.itemDisplay ? r.itemDisplay : r.reward;
                                    } else if (r.reward && r.reward.startsWith('play:2')) {
                                        b.classList.add('play2'); b.textContent = 'Play x2';
                                    } else if (r.reward && r.reward.startsWith('play:1')) {
                                        b.classList.add('play1'); b.textContent = 'Play x1';
                                    } else if (r.reward && r.reward.startsWith('points:')) {
                                        b.classList.add('points50'); b.textContent = '50 pts';
                                    } else {
                                        b.classList.add('none'); b.textContent = r.reward;
                                    }
                                }
                                // preview only: do not change displayed stats
                            }
                        }).catch(()=>{});
                }
            } catch(e) {}
        });
    </script>
    <script>
        // clear picked boxes when leaving the mystery page so boxes reset
        window.addEventListener('beforeunload', function() {
            try {
                navigator.sendBeacon('clearpicked');
            } catch(e) {
                // best-effort
                fetch('clearpicked', {method:'POST', keepalive:true}).catch(()=>{});
            }
        });
    </script>
</head>
<body>
    <div class="topbar">
        <div>Player: <b><%= user %></b></div>
        <div>Points: <span id="points"><%= session.getAttribute("points") != null ? session.getAttribute("points") : 0 %></span> | Plays: <span id="plays"><%= plays %></span></div>
    </div>
    <div class="grid">
        <% for (int i=0;i<16;i++) { %>
            <div class="box" onclick="openBox(this,<%=i%>)">Box <%=i+1%></div>
        <% } %>
    </div>
    <p style="text-align:center"><a href="game">Back to game</a></p>
</body>
</html>
