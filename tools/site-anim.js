// 기법별 동작 애니메이션. build-site.mjs가 site-template.html 안에 그대로 끼워 넣는다.
// 각 애니메이션은 ① 무작위 입력을 만들고 ② 알고리즘을 실제로 돌리며 단계마다 상태 스냅숏(frame)을 쌓고
// ③ 프레임 하나를 SVG로 그린다. 프레임을 미리 다 만들어 두므로 앞뒤로 자유롭게 넘길 수 있다.
// (esc, app, $, techCount, repCandidates, href, platName 등은 본문 스크립트에 있고, 호출 시점에만 쓴다)

function mulberry32(a) {
  return function () {
    a |= 0; a = a + 0x6D2B79F5 | 0;
    let t = Math.imul(a ^ a >>> 15, 1 | a);
    t = t + Math.imul(t ^ t >>> 7, 61 | t) ^ t;
    return ((t ^ t >>> 14) >>> 0) / 4294967296;
  };
}
const rint = (rng, lo, hi) => lo + Math.floor(rng() * (hi - lo + 1));
function shuffle(rng, arr) {
  for (let i = arr.length - 1; i > 0; i--) { const j = Math.floor(rng() * (i + 1)); [arr[i], arr[j]] = [arr[j], arr[i]]; }
  return arr;
}
// 숫자·글자 뒤에 붙는 조사를 받침에 맞게 고른다. J(5, '을/를') → "5를", J(3, '으로/로') → "3으로"
function J(w, pair) {
  const [a, b] = pair.split('/'), str = String(w), ch = str.replace(/\)+$/, '').slice(-1);
  let batchim = false, rieul = false;
  if (/[0-9]/.test(ch)) { batchim = '01366780'.includes(ch) && ch !== '2'; rieul = '178'.includes(ch); }
  else if (/[가-힣]/.test(ch)) { const k = (ch.charCodeAt(0) - 0xAC00) % 28; batchim = k > 0; rieul = k === 8; }
  if (a === '으로') return str + (batchim && !rieul ? '으로' : '로');
  return str + (batchim ? a : b);
}
function recorder() {
  const frames = [];
  return { frames, push(line, msg, st, vars) { frames.push({ line, msg, vars: vars || [], s: structuredClone(st) }); } };
}
// 목록을 짧게: 앞에서 max개 (fromEnd면 뒤에서 max개)
function listText(arr, max = 8, fromEnd = false) {
  if (!arr.length) return '비어 있음';
  if (arr.length <= max) return arr.join(' ');
  return fromEnd ? '… ' + arr.slice(-max).join(' ') : arr.slice(0, max).join(' ') + ` … 외 ${arr.length - max}개`;
}

// ───────── 격자 ─────────
const DIRS = [[-1, 0], [0, 1], [1, 0], [0, -1]];
function nbrs(u, R, C) {
  const r = Math.floor(u / C), c = u % C, out = [];
  for (const [dr, dc] of DIRS) { const nr = r + dr, nc = c + dc; if (nr >= 0 && nr < R && nc >= 0 && nc < C) out.push(nr * C + nc); }
  return out;
}
function bfsDist(wall, R, C, s) {
  const d = Array(R * C).fill(-1); d[s] = 0; const q = [s];
  while (q.length) { const u = q.shift(); for (const v of nbrs(u, R, C)) if (!wall[v] && d[v] < 0) { d[v] = d[u] + 1; q.push(v); } }
  return d;
}
// 시작(왼쪽 열)에서 목표(오른쪽 열)까지 길이 있는 미로
function makeMaze(rng, R, C, p) {
  for (let t = 0; t < 300; t++) {
    const wall = Array.from({ length: R * C }, () => rng() < p);
    const s = rint(rng, 0, R - 1) * C, g = rint(rng, 0, R - 1) * C + C - 1;
    wall[s] = wall[g] = false;
    if (bfsDist(wall, R, C, s)[g] >= C) return { wall, s, g };
  }
  return { wall: Array(R * C).fill(false), s: 0, g: R * C - 1 };
}
const rc = (u, C) => `(${Math.floor(u / C)},${u % C})`;

function gridSvg(R, C, cell) {
  const S = Math.min(52, Math.floor(472 / C)), W = C * S + 8, H = R * S + 8;
  let s = '';
  for (let i = 0; i < R * C; i++) {
    const x = 4 + (i % C) * S, y = 4 + Math.floor(i / C) * S, o = cell(i);
    s += `<g class="ac ${o.cls || ''}"><rect x="${x + 1.5}" y="${y + 1.5}" width="${S - 3}" height="${S - 3}" rx="6"/>`;
    if (o.t != null && o.t !== '') s += `<text x="${x + S / 2}" y="${y + S / 2 + 5}" text-anchor="middle">${o.t}</text>`;
    if (o.corner != null) s += `<text class="cn" x="${x + 6}" y="${y + 14}">${o.corner}</text>`;
    if (o.sub != null) s += `<text class="cn" x="${x + S - 6}" y="${y + S - 6}" text-anchor="end">${o.sub}</text>`;
    s += '</g>';
  }
  return `<svg class="anim-svg" viewBox="0 0 ${W} ${H}" role="img" aria-label="격자">${s}</svg>`;
}

// ───────── 배열 ─────────
// opt: cls(i), marks {i: ['lo', ...]}, range [a, b, label], caption, base(첫 인덱스 번호)
function arraySvg(vals, opt = {}) {
  const n = vals.length, S = Math.min(46, Math.floor(472 / n)), W = n * S + 8;
  const top = opt.range || opt.caption ? 34 : 8, H = top + S + 52;
  let s = '';
  if (opt.caption) s += `<text class="cap" x="4" y="16">${opt.caption}</text>`;
  if (opt.range) {
    const [a, b, label] = opt.range, x1 = 4 + a * S + 3, x2 = 4 + (b + 1) * S - 3, y = top - 8;
    s += `<g class="rng"><path d="M${x1},${y + 5} V${y} H${x2} V${y + 5}"/><text x="${(x1 + x2) / 2}" y="${y - 5}" text-anchor="middle">${label}</text></g>`;
  }
  for (let i = 0; i < n; i++) {
    const x = 4 + i * S, cls = opt.cls ? opt.cls(i) : '';
    s += `<g class="ac ${cls}"><rect x="${x + 1.5}" y="${top}" width="${S - 3}" height="${S - 3}" rx="6"/><text x="${x + S / 2}" y="${top + S / 2 + 4}" text-anchor="middle">${vals[i] ?? ''}</text></g>`;
    s += `<text class="cn" x="${x + S / 2}" y="${top + S + 12}" text-anchor="middle">${i + (opt.base || 0)}</text>`;
    const mk = opt.marks?.[i];
    if (mk?.length) s += `<text class="mk${mk.includes('mid') || mk.includes('r') ? ' strong' : ''}" x="${x + S / 2}" y="${top + S + 34}" text-anchor="middle">▲ ${mk.join('·')}</text>`;
  }
  return `<svg class="anim-svg" viewBox="0 0 ${W} ${H}" role="img" aria-label="배열">${s}</svg>`;
}

// ───────── 그래프 ─────────
const GPOS = [[50, 150], [150, 58], [150, 242], [255, 150], [360, 58], [360, 242], [450, 150]];
const GCAND = [[0, 1], [0, 2], [1, 2], [1, 3], [2, 3], [1, 4], [3, 4], [3, 5], [2, 5], [4, 5], [4, 6], [5, 6]];
const GL = i => 'ABCDEFG'[i];
// 연결된 무방향 그래프 (서로 다른 가중치)
function makeGraph(rng) {
  const cand = shuffle(rng, GCAND.map(e => [...e])), par = [0, 1, 2, 3, 4, 5, 6];
  const find = x => par[x] === x ? x : (par[x] = find(par[x]));
  const pick = [];
  for (const [u, v] of cand) { if (find(u) !== find(v)) { par[find(u)] = find(v); pick.push([u, v]); } else if (rng() < 0.55) pick.push([u, v]); }
  const ws = shuffle(rng, Array.from({ length: 14 }, (_, i) => i + 1));
  return pick.map(([u, v], i) => ({ u: Math.min(u, v), v: Math.max(u, v), w: ws[i] })).sort((a, b) => a.u - b.u || a.v - b.v);
}
function graphSvg(pos, edges, node, H = 300) {
  let s = `<defs><marker id="aarr" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="userSpaceOnUse" markerWidth="13" markerHeight="13" orient="auto-start-reverse"><path d="M0,1 L9,5 L0,9 z" class="arrowhead"/></marker></defs>`;
  for (const e of edges) {
    const [x1, y1] = pos[e.u], [x2, y2] = pos[e.v];
    const dx = x2 - x1, dy = y2 - y1, L = Math.hypot(dx, dy), r = 21, r2 = e.dir ? 25 : 21;
    s += `<g class="ae ${e.cls || ''}"><line x1="${x1 + dx / L * r}" y1="${y1 + dy / L * r}" x2="${x2 - dx / L * r2}" y2="${y2 - dy / L * r2}"${e.dir ? ' marker-end="url(#aarr)"' : ''}/>`;
    if (e.w != null) {
      const mx = (x1 + x2) / 2, my = (y1 + y2) / 2;
      s += `<rect class="wbg" x="${mx - 11}" y="${my - 10}" width="22" height="20" rx="6"/><text class="w" x="${mx}" y="${my + 4.5}" text-anchor="middle">${e.w}</text>`;
    }
    s += '</g>';
  }
  pos.forEach(([x, y], i) => {
    const o = node(i);
    s += `<g class="an ${o.cls || ''}"><circle cx="${x}" cy="${y}" r="20"/><text x="${x}" y="${y + 5}" text-anchor="middle">${o.label}</text>`;
    if (o.badge != null) {
      const by = y > H - 90 ? y + 36 : y - 30;
      s += `<g class="badge ${o.badgeCls || ''}"><rect x="${x - 19}" y="${by - 12}" width="38" height="19" rx="9.5"/><text x="${x}" y="${by + 2}" text-anchor="middle">${o.badge}</text></g>`;
    }
    s += '</g>';
  });
  return `<svg class="anim-svg" viewBox="0 0 500 ${H}" role="img" aria-label="그래프">${s}</svg>`;
}

// ═══════════════ 애니메이션 정의 ═══════════════
const ANIM = {};

ANIM.bfs = {
  name: 'BFS', title: '격자에서 최단 거리 찾기',
  desc: '큐를 써서 시작 칸에서 가까운 칸부터 차례로 퍼져 나갑니다. 그래서 목표에 처음 닿았을 때의 거리가 곧 최단 거리입니다. 격자 이동(dx/dy)도 같은 방식으로 상하좌우를 봅니다.',
  code: [
    'q.add(start); dist[start] = 0;',
    'while (!q.isEmpty()) {',
    '    cur = q.poll();',
    '    if (cur == goal) break;',
    '    for (int d = 0; d < 4; d++) {',
    '        nxt = cur + 방향[d];',
    '        if (범위 안 && !벽 && dist[nxt] == -1) {',
    '            dist[nxt] = dist[cur] + 1;',
    '            q.add(nxt);',
    '        }',
    '    }',
    '}',
  ],
  legend: [['cur', '지금 꺼낸 칸'], ['fresh', '방금 큐에 넣은 칸'], ['front', '큐에서 기다리는 칸'], ['vis', '방문함 (숫자 = 거리)'], ['path', '최단 경로'], ['wall', '벽']],
  gen(rng) {
    const R = 7, C = 10, { wall, s, g } = makeMaze(rng, R, C, 0.27);
    const st = { R, C, wall, s, g, dist: Array(R * C).fill(-1), q: [], cur: -1, fresh: [], path: [] };
    const prev = Array(R * C).fill(-1), rec = recorder();
    const vars = () => [['큐', listText(st.q.map(u => rc(u, C)), 7)], ['방문한 칸', st.dist.filter(d => d >= 0).length + '개']];
    st.dist[s] = 0; st.q.push(s);
    rec.push(0, `시작 칸 ${J(rc(s, C), '을/를')} 큐에 넣고 거리를 0으로 적습니다.`, st, vars());
    while (st.q.length) {
      st.cur = st.q.shift(); st.fresh = [];
      rec.push(2, `큐 맨 앞의 ${J(rc(st.cur, C), '을/를')} 꺼냅니다. 시작에서 ${st.dist[st.cur]}칸 떨어진 칸입니다.`, st, vars());
      if (st.cur === g) {
        const path = [g]; while (path[0] !== s) path.unshift(prev[path[0]]);
        st.path = path;
        rec.push(3, `목표에 도착했습니다. 가까운 칸부터 퍼졌으니 ${st.dist[g]}칸이 최단 거리이고, 각 칸을 처음 발견한 칸을 거꾸로 따라가면 경로가 나옵니다.`, st, vars());
        break;
      }
      for (const v of nbrs(st.cur, R, C)) if (!wall[v] && st.dist[v] < 0) { st.dist[v] = st.dist[st.cur] + 1; prev[v] = st.cur; st.q.push(v); st.fresh.push(v); }
      if (st.fresh.length) rec.push(8, `상하좌우 중 처음 가는 ${st.fresh.length}칸에 거리 ${J(st.dist[st.cur] + 1, '을/를')} 적고 큐 뒤에 넣습니다.`, st, vars());
      else rec.push(6, '상하좌우에 새로 갈 칸이 없습니다. (벽이거나, 범위 밖이거나, 이미 방문)', st, vars());
    }
    return rec.frames;
  },
  draw(f) {
    const s = f.s;
    return gridSvg(s.R, s.C, i => {
      if (s.wall[i]) return { cls: 'wall' };
      const cls = s.path.includes(i) ? 'path' : i === s.cur ? 'cur' : s.fresh.includes(i) ? 'fresh' : s.q.includes(i) ? 'front' : s.dist[i] >= 0 ? 'vis' : '';
      const label = i === s.s ? 'S' : i === s.g ? 'G' : null;
      return s.dist[i] >= 0 ? { cls, t: s.dist[i], corner: label } : { cls, t: label || '' };
    });
  },
};

ANIM.dfs = {
  name: 'DFS', title: '재귀로 길 찾기',
  desc: '갈 수 있는 방향으로 끝까지 들어가 보고, 막히면 직전 칸으로 되돌아와 다른 방향을 시도합니다. 호출 스택이 곧 지금까지 온 길이고, 찾은 길이 최단 경로라는 보장은 없습니다.',
  code: [
    'boolean dfs(cur) {',
    '    visited[cur] = true;',
    '    if (cur == goal) return true;',
    '    for (int d = 0; d < 4; d++) {',
    '        nxt = cur + 방향[d];',
    '        if (범위 안 && !벽 && !visited[nxt])',
    '            if (dfs(nxt)) return true;',
    '    }',
    '    return false;  // 막힘 → 되돌아감',
    '}',
  ],
  legend: [['cur', '지금 칸'], ['vis', '호출 스택에 있는 칸 (숫자 = 방문 순서)'], ['dead', '막혀서 되돌아온 칸'], ['path', '찾은 경로'], ['wall', '벽']],
  gen(rng) {
    const R = 6, C = 9, { wall, s, g } = makeMaze(rng, R, C, 0.25);
    const st = { R, C, wall, s, g, state: Array(R * C).fill(0), order: Array(R * C).fill(0), stack: [], cur: -1, done: false };
    const rec = recorder(); let cnt = 0;
    const vars = () => [['호출 스택', listText(st.stack.map(u => rc(u, C)), 6, true)], ['호출 깊이', st.stack.length], ['방문한 칸', cnt + '개']];
    const dfs = u => {
      st.stack.push(u); st.state[u] = 1; st.order[u] = ++cnt; st.cur = u;
      rec.push(1, `dfs${J(rc(u, C), '을/를')} 호출합니다. ${cnt}번째로 방문한 칸이고, 호출 깊이는 ${st.stack.length}입니다.`, st, vars());
      if (u === g) {
        st.done = true;
        rec.push(2, `목표에 도착했습니다! 호출 스택에 쌓인 ${st.stack.length}칸이 찾은 길입니다. BFS와 달리 최단 경로가 아닐 수 있습니다.`, st, vars());
        return true;
      }
      for (const v of nbrs(u, R, C)) if (!wall[v] && !st.state[v]) if (dfs(v)) return true;
      st.state[u] = 2; st.stack.pop(); st.cur = st.stack.at(-1) ?? -1;
      rec.push(8, `${rc(u, C)}에서는 더 갈 곳이 없습니다. false를 반환하고 ${st.cur >= 0 ? J(rc(st.cur, C), '으로/로') : ''} 되돌아갑니다.`, st, vars());
      return false;
    };
    dfs(s);
    return rec.frames;
  },
  draw(f) {
    const s = f.s;
    return gridSvg(s.R, s.C, i => {
      if (s.wall[i]) return { cls: 'wall' };
      const cls = s.done && s.state[i] === 1 ? 'path' : i === s.cur ? 'cur' : s.state[i] === 1 ? 'vis' : s.state[i] === 2 ? 'dead' : '';
      const label = i === s.s ? 'S' : i === s.g ? 'G' : null;
      return s.order[i] ? { cls, t: s.order[i], corner: label } : { cls, t: label || '' };
    });
  },
};

ANIM.backtrack = {
  name: '백트래킹', title: 'N-Queen: 놓아 보고, 안 되면 되돌리기',
  desc: '한 줄에 퀸을 하나씩 놓습니다. 이미 놓인 퀸과 같은 열이나 대각선이면 건너뛰고, 다음 줄에 놓을 곳이 하나도 없으면 직전에 놓은 퀸을 치우고 옆 칸을 시도합니다. "새 입력"을 누르면 판 크기가 바뀝니다.',
  code: [
    'boolean solve(int row) {',
    '    if (row == N) return true;  // 모두 놓음',
    '    for (int col = 0; col < N; col++) {',
    '        if (!놓을수있음(row, col)) continue;',
    '        board[row] = col;       // 놓기',
    '        if (solve(row + 1)) return true;',
    '        board[row] = -1;        // 되돌리기',
    '    }',
    '    return false;',
    '}',
  ],
  legend: [['trysq', '지금 보는 칸'], ['bad', '놓을 수 없는 칸 / 겹치는 퀸'], ['att', '이미 놓인 퀸이 공격하는 칸'], ['path', '완성된 배치']],
  gen(rng) {
    const N = [6, 5, 7, 4][Math.floor(rng() * 4)];
    const st = { N, board: Array(N).fill(-1), row: -1, col: -1, bad: [], mode: '' };
    const rec = recorder(); let tries = 0, backs = 0;
    const vars = () => [['판 크기', `${N} × ${N}`], ['놓은 퀸', st.board.filter(c => c >= 0).length + '개'], ['시도한 칸', tries + '번'], ['되돌린 횟수', backs + '번']];
    const attackers = (r, c) => { const out = []; for (let i = 0; i < r; i++) { const q = st.board[i]; if (q === c || Math.abs(q - c) === r - i) out.push(i); } return out; };
    const solve = r => {
      if (r === N) { st.row = st.col = -1; st.mode = 'done'; rec.push(1, `${N}개를 모두 놓았습니다. 해를 찾았습니다!`, st, vars()); return true; }
      for (let c = 0; c < N; c++) {
        st.row = r; st.col = c; tries++;
        const bad = attackers(r, c);
        if (bad.length) {
          st.bad = bad; st.mode = 'bad';
          rec.push(3, `${r}번 줄 ${c}번 칸은 ${bad.map(i => i + '번 줄').join(', ')} 퀸과 같은 열이나 대각선이라 놓을 수 없습니다.`, st, vars());
          st.bad = []; continue;
        }
        st.board[r] = c; st.mode = 'place';
        rec.push(4, `${r}번 줄 ${c}번 칸은 안전합니다. 퀸을 놓고 다음 줄로 내려갑니다.`, st, vars());
        if (solve(r + 1)) return true;
        st.board[r] = -1; backs++; st.row = r; st.col = c; st.mode = 'undo';
        rec.push(6, `아래 줄에서 막혔습니다. ${r}번 줄 ${c}번 칸의 퀸을 치우고 옆 칸을 시도합니다.`, st, vars());
      }
      st.row = r; st.col = -1; st.mode = 'fail';
      rec.push(8, `${r}번 줄에는 놓을 곳이 없습니다. false를 반환해 윗줄로 되돌아갑니다.`, st, vars());
      return false;
    };
    rec.push(0, `${N}×${N} 판의 0번 줄부터 시작합니다. 퀸끼리 같은 열·대각선에 있으면 안 됩니다.`, st, vars());
    solve(0);
    return rec.frames;
  },
  draw(f) {
    const s = f.s, N = s.N, S = Math.min(54, Math.floor(372 / N)), W = N * S + 8;
    let out = '';
    for (let r = 0; r < N; r++) for (let c = 0; c < N; c++) {
      const x = 4 + c * S, y = 4 + r * S;
      const att = s.mode !== 'done' && s.board.some((q, i) => q >= 0 && i !== r && (q === c || Math.abs(q - c) === Math.abs(i - r)));
      const isTry = r === s.row && c === s.col;
      let cls = (r + c) % 2 ? 'sq dark' : 'sq';
      if (att) cls += ' att';
      if (isTry) cls += s.mode === 'bad' ? ' trybad' : s.mode === 'undo' ? ' tryundo' : ' try';
      if (r === s.row && s.mode === 'fail') cls += ' rowfail';
      out += `<g class="ac ${cls}"><rect x="${x + 1}" y="${y + 1}" width="${S - 2}" height="${S - 2}" rx="4"/></g>`;
      if (s.board[r] === c) {
        out += `<g class="queen${s.bad.includes(r) ? ' bad' : ''}${s.mode === 'done' ? ' win' : ''}"><circle cx="${x + S / 2}" cy="${y + S / 2}" r="${S * 0.32}"/><text x="${x + S / 2}" y="${y + S / 2 + 5}" text-anchor="middle">Q</text></g>`;
      } else if (isTry && s.mode === 'bad') {
        out += `<text class="xmark" x="${x + S / 2}" y="${y + S / 2 + 7}" text-anchor="middle">✕</text>`;
      }
    }
    return `<svg class="anim-svg" viewBox="0 0 ${W} ${W}" style="max-width:${Math.round(W * 1.15)}px" role="img" aria-label="체스판">${out}</svg>`;
  },
};

ANIM.dp = {
  name: 'DP', title: '격자에서 합이 가장 큰 경로',
  desc: '오른쪽·아래로만 움직일 때 각 칸까지 올 수 있는 최대 합을 표에 채웁니다. 한 칸의 답은 이미 구해 둔 위·왼쪽 칸의 답만 보면 정해지므로, 모든 경로를 다 따져 보지 않아도 됩니다.',
  code: [
    'dp[0][0] = a[0][0];',
    'for (int i = 0; i < R; i++)',
    '    for (int j = 0; j < C; j++) {',
    '        int 위 = dp[i-1][j], 왼 = dp[i][j-1];',
    '        dp[i][j] = a[i][j] + Math.max(위, 왼);',
    '    }',
    '// 답 = dp[R-1][C-1]',
  ],
  legend: [['cur', '지금 계산하는 칸'], ['dep', '참고하는 칸 (위·왼쪽)'], ['vis', '계산 끝 (큰 숫자 = dp, 작은 숫자 = 칸 값)'], ['path', '최대 합 경로']],
  gen(rng) {
    const R = 5, C = 7, a = Array.from({ length: R * C }, () => rint(rng, 0, 9));
    const st = { R, C, a, dp: Array(R * C).fill(null), from: Array(R * C).fill(''), cur: -1, deps: [], path: [] };
    const rec = recorder();
    const vars = expr => [['계산한 칸', `${st.dp.filter(v => v != null).length} / ${R * C}`], ['식', expr || '-']];
    for (let i = 0; i < R; i++) for (let j = 0; j < C; j++) {
      const k = i * C + j, up = i ? k - C : -1, lf = j ? k - 1 : -1;
      st.cur = k; st.deps = [up, lf].filter(x => x >= 0);
      if (!st.deps.length) {
        st.dp[k] = a[k];
        rec.push(0, `출발 칸 (0,0)은 자기 값 ${J(a[k], '이/가')} 곧 답입니다.`, st, vars(`dp[0][0] = ${a[k]}`));
        continue;
      }
      const best = st.deps.reduce((b, x) => st.dp[x] > st.dp[b] ? x : b);
      st.dp[k] = a[k] + st.dp[best]; st.from[k] = best === up ? '↑' : '←';
      if (st.deps.length === 2) {
        rec.push(4, `(${i},${j}): 위(${st.dp[up]})와 왼쪽(${st.dp[lf]}) 중 큰 ${st.dp[best]}에 이 칸 값 ${J(a[k], '을/를')} 더합니다.`, st,
          vars(`dp[${i}][${j}] = ${a[k]} + max(${st.dp[up]}, ${st.dp[lf]}) = ${st.dp[k]}`));
      } else {
        rec.push(4, `(${i},${j}): ${i ? '첫 열이라 위' : '첫 줄이라 왼쪽'}에서만 올 수 있습니다. ${st.dp[best]} + ${a[k]} = ${st.dp[k]}`, st,
          vars(`dp[${i}][${j}] = ${a[k]} + ${st.dp[best]} = ${st.dp[k]}`));
      }
    }
    st.cur = -1; st.deps = [];
    let k = R * C - 1; const path = [k];
    while (k) { k = st.from[k] === '↑' ? k - C : k - 1; path.unshift(k); }
    st.path = path;
    rec.push(6, `표를 다 채웠습니다. 최대 합은 ${st.dp[R * C - 1]}이고, 각 칸의 화살표를 거꾸로 따라가면 그 경로가 나옵니다.`, st, vars(`답 = dp[${R - 1}][${C - 1}] = ${st.dp[R * C - 1]}`));
    return rec.frames;
  },
  draw(f) {
    const s = f.s;
    return gridSvg(s.R, s.C, i => {
      if (s.dp[i] == null) return { cls: 'raw', t: s.a[i] };
      const cls = s.path.includes(i) ? 'path' : i === s.cur ? 'cur' : s.deps.includes(i) ? 'dep' : 'vis';
      return { cls, t: s.dp[i], corner: s.a[i], sub: s.from[i] || null };
    });
  },
};

ANIM.binsearch = {
  name: '이분 탐색', title: '정렬된 배열에서 값 찾기',
  desc: '가운데 값과 비교해서, 답이 있을 수 없는 절반을 통째로 버립니다. 비교할 때마다 후보가 절반으로 줄어 n개라도 약 log₂n번이면 끝납니다. "답이 되는 최솟값 찾기" 같은 매개변수 탐색도 같은 틀입니다.',
  code: [
    'int lo = 0, hi = n - 1;',
    'while (lo <= hi) {',
    '    int mid = (lo + hi) / 2;',
    '    if (a[mid] == target) return mid;',
    '    else if (a[mid] < target) lo = mid + 1;',
    '    else hi = mid - 1;',
    '}',
    'return -1;   // 없음',
  ],
  legend: [['cur', 'mid (비교하는 칸)'], ['gone', '후보에서 빠진 칸'], ['path', '찾은 값']],
  gen(rng) {
    const n = 15, set = new Set();
    while (set.size < n) set.add(rint(rng, 1, 60));
    const a = [...set].sort((x, y) => x - y);
    let target;
    if (rng() < 0.75) target = a[rint(rng, 0, n - 1)]; else do target = rint(rng, 1, 60); while (set.has(target));
    const st = { a, target, lo: 0, hi: n - 1, mid: -1, found: -1 };
    const rec = recorder(); let cmp = 0;
    const vars = () => [['찾는 값', target], ['lo / hi', `${st.lo} / ${st.hi}`], ['mid', st.mid >= 0 ? st.mid : '-'], ['남은 후보', Math.max(0, st.hi - st.lo + 1) + '개'], ['비교 횟수', cmp + '번']];
    rec.push(0, `정렬된 ${n}개 값에서 ${J(target, '을/를')} 찾습니다. 처음에는 전체가 후보입니다.`, st, vars());
    while (st.lo <= st.hi) {
      st.mid = (st.lo + st.hi) >> 1; cmp++;
      const v = a[st.mid];
      rec.push(2, `가운데 mid = (${st.lo} + ${st.hi}) / 2 = ${st.mid}, a[mid] = ${J(v, '과/와')} 비교합니다.`, st, vars());
      if (v === target) {
        st.found = st.mid;
        rec.push(3, `${v} = ${target}, 찾았습니다! ${cmp}번 비교했습니다. 앞에서부터 하나씩 봤다면 ${st.mid + 1}번 걸렸을 거예요.`, st, vars());
        return rec.frames;
      }
      if (v < target) { st.lo = st.mid + 1; rec.push(4, `${v} < ${target}이므로 mid와 그 왼쪽에는 답이 없습니다. lo = ${st.lo}`, st, vars()); }
      else { st.hi = st.mid - 1; rec.push(5, `${v} > ${target}이므로 mid와 그 오른쪽에는 답이 없습니다. hi = ${st.hi}`, st, vars()); }
      st.mid = -1;
    }
    rec.push(7, `lo(${st.lo}) > hi(${st.hi}): 후보가 하나도 남지 않았으니 ${J(target, '은/는')} 배열에 없습니다.`, st, vars());
    return rec.frames;
  },
  draw(f) {
    const s = f.s, marks = {};
    const add = (i, m) => { if (i >= 0 && i < s.a.length) (marks[i] = marks[i] || []).push(m); };
    if (s.found < 0) { add(s.lo, 'lo'); add(s.hi, 'hi'); }
    add(s.mid >= 0 ? s.mid : s.found, 'mid');
    return arraySvg(s.a, {
      caption: `찾는 값 ${s.target}`, marks,
      cls: i => i === s.found ? 'path' : i === s.mid ? 'cur' : (i < s.lo || i > s.hi) ? 'gone' : '',
    });
  },
};

ANIM.twoptr = {
  name: '투 포인터', title: '합이 S 이상인 가장 짧은 구간',
  desc: '오른쪽 끝 r을 늘려 합을 키우고, 조건을 만족하는 동안 왼쪽 끝 l을 당겨 구간을 최대한 줄입니다. 두 포인터가 모두 앞으로만 움직이므로 전체가 O(n)입니다.',
  code: [
    'int l = 0, sum = 0, best = INF;',
    'for (int r = 0; r < n; r++) {',
    '    sum += a[r];          // 오른쪽 늘리기',
    '    while (sum >= S) {',
    '        best = Math.min(best, r - l + 1);',
    '        sum -= a[l++];    // 왼쪽 줄이기',
    '    }',
    '}',
    'return best;',
  ],
  legend: [['win', '지금 구간 [l, r]'], ['cur', '방금 움직인 칸'], ['path', '지금까지 가장 짧은 구간']],
  gen(rng) {
    const n = 12, a = Array.from({ length: n }, () => rint(rng, 1, 9)), S = rint(rng, 13, 22);
    const st = { a, S, l: 0, r: -1, sum: 0, best: Infinity, bl: -1, br: -1, moved: -1 };
    const rec = recorder();
    const vars = () => [['S', S], ['l / r', `${st.l} / ${st.r < 0 ? '-' : st.r}`], ['sum', st.sum], ['best', st.best === Infinity ? '∞' : st.best]];
    rec.push(0, `합이 ${S} 이상이 되는 가장 짧은 연속 구간을 찾습니다.`, st, vars());
    for (let r = 0; r < n; r++) {
      st.r = r; st.sum += a[r]; st.moved = r;
      rec.push(2, `r을 ${J(r, '으로/로')} 늘려 a[${r}] = ${J(a[r], '을/를')} 더합니다. 합 ${st.sum} ${st.sum >= S ? '≥' : '<'} ${S}`, st, vars());
      while (st.sum >= S) {
        const len = r - st.l + 1, better = len < st.best;
        if (better) { st.best = len; st.bl = st.l; st.br = r; }
        st.moved = -1;
        rec.push(4, `합 ${st.sum} ≥ ${S}이므로 [${st.l}, ${r}] (길이 ${len})도 답이 됩니다.${better ? ' 지금까지 가장 짧습니다.' : ''}`, st, vars());
        st.sum -= a[st.l]; st.moved = st.l; st.l++;
        rec.push(5, `왼쪽 a[${st.l - 1}] = ${J(a[st.l - 1], '을/를')} 빼고 l을 ${J(st.l, '으로/로')} 당깁니다. 합 ${st.sum}`, st, vars());
      }
    }
    st.moved = -1;
    rec.push(8, st.best < Infinity ? `끝까지 봤습니다. 가장 짧은 구간은 [${st.bl}, ${st.br}], 길이 ${st.best}입니다.` : `합이 ${S} 이상인 구간이 없습니다.`, st, vars());
    return rec.frames;
  },
  draw(f) {
    const s = f.s, marks = {};
    const add = (i, m) => { if (i >= 0 && i < s.a.length) (marks[i] = marks[i] || []).push(m); };
    if (s.r >= 0) { add(s.l, 'l'); add(s.r, 'r'); }
    return arraySvg(s.a, {
      marks, range: s.bl >= 0 ? [s.bl, s.br, `최단 ${s.best}`] : null,
      cls: i => i === s.moved ? 'cur' : (i >= s.l && i <= s.r) ? 'win' : (s.bl >= 0 && i >= s.bl && i <= s.br) ? 'pathsoft' : '',
    });
  },
};

ANIM.prefix = {
  name: '누적합', title: '구간 합을 뺄셈 한 번으로',
  desc: 'P[i] = a[1] + … + a[i]를 미리 한 번 계산해 두면, 어떤 구간 [l, r]의 합도 P[r] − P[l−1] 한 번으로 구합니다. 질의가 많을수록 차이가 커집니다.',
  code: [
    'P[0] = 0;',
    'for (int i = 1; i <= n; i++)',
    '    P[i] = P[i-1] + a[i];',
    '',
    '// 질의: 구간 [l, r]의 합',
    'int sum = P[r] - P[l-1];',
  ],
  legend: [['cur', '지금 구하는 값'], ['dep', '쓰는 값'], ['win', '질의 구간']],
  gen(rng) {
    const n = 9, a = [null, ...Array.from({ length: n }, () => rint(rng, 1, 9))];
    const st = { n, a, P: Array(n + 1).fill(null), cur: -1, deps: [], q: null };
    const rec = recorder(), res = [];
    const vars = () => [['P', st.P.map(v => v ?? '·').join(' ')], ['질의 결과', res.length ? res.join(', ') : '-']];
    st.P[0] = 0; st.cur = 0;
    rec.push(0, 'P[0] = 0으로 시작합니다. (아무것도 더하지 않은 합)', st, vars());
    for (let i = 1; i <= n; i++) {
      st.P[i] = st.P[i - 1] + a[i]; st.cur = i; st.deps = [i - 1];
      rec.push(2, `P[${i}] = P[${i - 1}] + a[${i}] = ${st.P[i - 1]} + ${a[i]} = ${st.P[i]}`, st, vars());
    }
    st.cur = -1; st.deps = [];
    for (let k = 0; k < 3; k++) {
      let l = rint(rng, 1, n - 1), r = rint(rng, l + 1, n);
      st.q = [l, r];
      const sum = st.P[r] - st.P[l - 1];
      res.push(`[${l},${r}]=${sum}`);
      rec.push(5, `구간 [${l}, ${r}]의 합 = P[${r}] − P[${l - 1}] = ${st.P[r]} − ${st.P[l - 1]} = ${sum}. 원소 ${r - l + 1}개를 하나씩 더하지 않아도 됩니다.`, st, vars());
    }
    return rec.frames;
  },
  draw(f) {
    const s = f.s, n = s.n, S = 44, X0 = 34, y1 = 26, y2 = y1 + S + 34;
    let o = `<text class="cap" x="6" y="${y1 + S / 2 + 5}">a</text><text class="cap" x="6" y="${y2 + S / 2 + 5}">P</text>`;
    for (let i = 0; i <= n; i++) {
      const x = X0 + i * S;
      o += `<text class="cn" x="${x + S / 2}" y="${y1 - 8}" text-anchor="middle">${i}</text>`;
      if (i >= 1) {
        const inQ = s.q && i >= s.q[0] && i <= s.q[1];
        const cls = inQ ? 'win' : (s.cur === i ? 'dep' : '');
        o += `<g class="ac ${cls}"><rect x="${x + 1.5}" y="${y1}" width="${S - 3}" height="${S - 3}" rx="6"/><text x="${x + S / 2}" y="${y1 + S / 2 + 4}" text-anchor="middle">${s.a[i]}</text></g>`;
      }
      const isQ = s.q && (i === s.q[1] || i === s.q[0] - 1);
      const cls = isQ ? 'cur' : i === s.cur ? 'cur' : s.deps.includes(i) ? 'dep' : s.P[i] != null ? 'vis' : '';
      o += `<g class="ac ${cls}"><rect x="${x + 1.5}" y="${y2}" width="${S - 3}" height="${S - 3}" rx="6"/><text x="${x + S / 2}" y="${y2 + S / 2 + 4}" text-anchor="middle">${s.P[i] ?? ''}</text></g>`;
    }
    if (s.q) {
      const [l, r] = s.q, xr = X0 + r * S + S / 2, xl = X0 + (l - 1) * S + S / 2, y = y2 + S + 16;
      o += `<text class="mk strong" x="${xr}" y="${y}" text-anchor="middle">P[r]</text><text class="mk" x="${xl}" y="${y}" text-anchor="middle">P[l−1]</text>`;
    }
    const W = X0 + (n + 1) * S + 4, H = y2 + S + 26;
    return `<svg class="anim-svg" viewBox="0 0 ${W} ${H}" role="img" aria-label="누적합 배열">${o}</svg>`;
  },
};

ANIM.pq = {
  name: '우선순위 큐', title: '최소 힙에 넣고 꺼내기',
  desc: 'PriorityQueue는 내부적으로 힙(완전 이진 트리)입니다. 부모가 항상 자식보다 작게 유지되므로 맨 위가 언제나 최솟값이고, 넣기·꺼내기 모두 트리 높이(log n)만큼만 자리를 바꿉니다.',
  code: [
    'void add(x) {',
    '    heap[size++] = x;        // 맨 끝에',
    '    while (부모 > 나) 교환;   // 위로',
    '}',
    'int poll() {',
    '    top = heap[0]; heap[0] = heap[--size];',
    '    while (작은 자식 < 나) 교환; // 아래로',
    '    return top;',
    '}',
  ],
  legend: [['cur', '움직이는 값'], ['dep', '비교 상대']],
  gen(rng) {
    const set = new Set(); while (set.size < 7) set.add(rint(rng, 1, 50));
    const vals = [...set];
    const st = { heap: [], hl: -1, cmp: -1, out: [] };
    const rec = recorder();
    const vars = () => [['배열', '[' + st.heap.join(', ') + ']'], ['넣을 값', listText(vals.slice(st.heap.length + st.out.length), 7)], ['꺼낸 순서', st.out.join(', ') || '-']];
    const H = st.heap, sw = (i, j) => { [H[i], H[j]] = [H[j], H[i]]; };
    for (const x of vals) {
      H.push(x); let i = H.length - 1; st.hl = i; st.cmp = -1;
      rec.push(1, `${J(x, '을/를')} 배열 맨 끝(${i}번)에 넣습니다.`, st, vars());
      while (i > 0) {
        const p = (i - 1) >> 1; st.cmp = p;
        if (H[p] <= H[i]) { rec.push(2, `부모 ${H[p]} ≤ ${x}이므로 여기서 멈춥니다.`, st, vars()); break; }
        sw(i, p); i = p; st.hl = i; st.cmp = -1;
        rec.push(2, `부모가 더 커서 ${J(x, '과/와')} 자리를 바꿉니다.`, st, vars());
      }
      if (i === 0 && H.length > 1 && st.cmp === -1) { rec.push(2, `${J(x, '이/가')} 맨 위까지 올라왔습니다. 지금 최솟값입니다.`, st, vars()); }
    }
    for (let k = 0; k < 3; k++) {
      const top = H[0], last = H.pop();
      if (H.length) H[0] = last;
      st.out.push(top); st.hl = 0; st.cmp = -1;
      rec.push(5, `맨 위의 최솟값 ${J(top, '을/를')} 꺼내고, 맨 끝 값 ${J(last, '을/를')} 맨 위로 옮깁니다.`, st, vars());
      let i = 0;
      for (;;) {
        const l = 2 * i + 1, r = l + 1;
        if (l >= H.length) { rec.push(7, `${H[i]}의 자식이 없어 자리가 정해졌습니다. ${J(top, '을/를')} 반환합니다.`, st, vars()); break; }
        const c = r < H.length && H[r] < H[l] ? r : l; st.cmp = c;
        if (H[c] >= H[i]) { rec.push(7, `작은 자식 ${H[c]} ≥ ${H[i]}이므로 자리가 정해졌습니다. ${J(top, '을/를')} 반환합니다.`, st, vars()); break; }
        const x = H[i]; sw(i, c); i = c; st.hl = i; st.cmp = -1;
        rec.push(6, `두 자식 중 작은 ${J(H[(i - 1) >> 1], '이/가')} ${x}보다 작아서 자리를 바꿉니다.`, st, vars());
      }
    }
    return rec.frames;
  },
  draw(f) {
    const s = f.s, H = s.heap;
    const pos = i => { const L = Math.floor(Math.log2(i + 1)), k = i - (2 ** L - 1); return [(k + 0.5) * 480 / 2 ** L + 10, 34 + L * 78]; };
    let o = '';
    for (let i = 1; i < H.length; i++) { const [x1, y1] = pos((i - 1) >> 1), [x2, y2] = pos(i); o += `<g class="ae"><line x1="${x1}" y1="${y1}" x2="${x2}" y2="${y2}"/></g>`; }
    for (let i = 0; i < H.length; i++) {
      const [x, y] = pos(i), cls = i === s.hl ? 'cur' : i === s.cmp ? 'cmp' : '';
      o += `<g class="an ${cls}"><circle cx="${x}" cy="${y}" r="21"/><text x="${x}" y="${y + 5}" text-anchor="middle">${H[i]}</text></g>`;
    }
    const S = 44, n = 7, X0 = (500 - n * S) / 2, Y = 34 + 2 * 78 + 66;
    o += `<text class="cap" x="${X0}" y="${Y - 10}">배열로 저장된 모습 (i의 자식 = 2i+1, 2i+2)</text>`;
    for (let i = 0; i < n; i++) {
      const x = X0 + i * S, cls = i >= H.length ? 'gone' : i === s.hl ? 'cur' : i === s.cmp ? 'dep' : '';
      o += `<g class="ac ${cls}"><rect x="${x + 1.5}" y="${Y}" width="${S - 3}" height="${S - 3}" rx="6"/><text x="${x + S / 2}" y="${Y + S / 2 + 4}" text-anchor="middle">${H[i] ?? ''}</text></g><text class="cn" x="${x + S / 2}" y="${Y + S + 12}" text-anchor="middle">${i}</text>`;
    }
    return `<svg class="anim-svg" viewBox="0 0 500 ${Y + S + 20}" role="img" aria-label="힙">${o}</svg>`;
  },
};

ANIM.dijkstra = {
  name: '다익스트라', title: '한 정점에서 모든 정점까지 최단 거리',
  desc: '우선순위 큐에서 지금까지의 거리가 가장 짧은 정점을 꺼내 확정하고, 그 정점을 거쳐 가면 더 짧아지는 이웃의 거리를 줄입니다. 간선 가중치가 음수가 아닐 때 쓸 수 있습니다. 정점 위의 숫자가 A에서의 거리입니다.',
  code: [
    'dist[] = INF; dist[A] = 0; pq.add({0, A});',
    'while (!pq.isEmpty()) {',
    '    {d, u} = pq.poll();',
    '    if (d > dist[u]) continue;  // 이미 확정',
    '    for ({v, w} : adj[u])',
    '        if (dist[u] + w < dist[v]) {',
    '            dist[v] = dist[u] + w;',
    '            pq.add({dist[v], v});',
    '        }',
    '}',
  ],
  legend: [['done', '거리 확정'], ['curn', '지금 꺼낸 정점'], ['front', 'pq에 들어 있음'], ['try', '지금 보는 간선'], ['tree', '최단 경로에 쓰인 간선']],
  gen(rng) {
    const E = makeGraph(rng), n = 7;
    const st = { E, dist: Array(n).fill(Infinity), done: Array(n).fill(false), pq: [], cur: -1, edge: -1, prev: Array(n).fill(-1) };
    const rec = recorder();
    const vars = () => [['pq', st.pq.length ? [...st.pq].sort((a, b) => a[0] - b[0]).map(([d, u]) => `(${d},${GL(u)})`).join(' ') : '비어 있음'], ['확정', st.done.map((x, i) => x ? GL(i) : '').filter(Boolean).join(' ') || '-']];
    st.dist[0] = 0; st.pq.push([0, 0]);
    rec.push(0, '출발점 A의 거리는 0, 나머지는 ∞로 두고 (0, A)를 pq에 넣습니다.', st, vars());
    while (st.pq.length) {
      st.pq.sort((a, b) => a[0] - b[0] || a[1] - b[1]);
      const [d, u] = st.pq.shift(); st.cur = u; st.edge = -1;
      if (d > st.dist[u]) { rec.push(3, `(${d}, ${GL(u)})를 꺼냈지만 ${J(GL(u), '은/는')} 이미 ${J(st.dist[u], '으로/로')} 확정됐으므로 건너뜁니다.`, st, vars()); continue; }
      st.done[u] = true;
      rec.push(2, `pq에서 거리가 가장 짧은 (${d}, ${GL(u)})를 꺼냅니다. 이보다 짧게 올 방법은 없으니 ${GL(u)}의 최단 거리는 ${J(d, '으로/로')} 확정입니다.`, st, vars());
      E.forEach((e, k) => {
        if (e.u !== u && e.v !== u) return;
        const v = e.u === u ? e.v : e.u;
        if (st.done[v]) return;
        st.edge = k; const nd = d + e.w;
        if (nd < st.dist[v]) {
          const old = st.dist[v]; st.dist[v] = nd; st.prev[v] = k; st.pq.push([nd, v]);
          rec.push(6, `${J(GL(u), '을/를')} 거쳐 ${GL(v)}로: ${d} + ${e.w} = ${nd} < ${old === Infinity ? '∞' : old}이므로 거리를 ${J(nd, '으로/로')} 줄이고 pq에 넣습니다.`, st, vars());
        } else {
          rec.push(5, `${J(GL(u), '을/를')} 거쳐 ${GL(v)}로: ${d} + ${e.w} = ${nd} ≥ ${st.dist[v]}이라 더 짧지 않습니다. 그대로 둡니다.`, st, vars());
        }
      });
    }
    st.cur = -1; st.edge = -1;
    rec.push(1, 'pq가 비었습니다. 모든 정점의 최단 거리가 확정됐고, 파란 굵은 선을 따라가면 A에서 오는 최단 경로입니다.', st, vars());
    return rec.frames;
  },
  draw(f) {
    const s = f.s, inPq = new Set(s.pq.map(p => p[1])), tree = new Set(s.prev.filter(k => k >= 0));
    return graphSvg(GPOS, s.E.map((e, k) => ({ ...e, cls: k === s.edge ? 'try' : tree.has(k) ? 'tree' : '' })), i => ({
      label: GL(i),
      cls: [s.done[i] ? 'done' : inPq.has(i) ? 'front' : '', i === s.cur ? 'curn' : ''].join(' '),
      badge: s.dist[i] === Infinity ? '∞' : s.dist[i], badgeCls: s.done[i] ? 'done' : '',
    }));
  },
};

ANIM.uf = {
  name: '유니온 파인드', title: '집합 합치기와 대표 찾기',
  desc: '각 원소는 부모를 가리키고, 부모를 따라 올라가 닿는 루트가 그 집합의 대표입니다. 두 원소의 루트가 같으면 같은 집합입니다. find 중에 지나온 원소를 루트에 바로 붙이면(경로 압축) 다음 find가 빨라집니다.',
  code: [
    'int find(int x) {',
    '    if (parent[x] == x) return x;',
    '    return parent[x] = find(parent[x]); // 경로 압축',
    '}',
    'void union(int a, int b) {',
    '    int ra = find(a), rb = find(b);',
    '    if (ra != rb) parent[rb] = ra;',
    '}',
  ],
  legend: [['curn', 'find가 지나는 원소'], ['root', '루트 (집합의 대표)']],
  gen(rng) {
    const n = 8, st = { parent: Array.from({ length: n }, (_, i) => i), hl: [], op: '' };
    const rec = recorder(), P = st.parent;
    const groups = () => {
      const g = {}; for (let i = 0; i < n; i++) { let r = i; while (P[r] !== r) r = P[r]; (g[r] = g[r] || []).push(i + 1); }
      return Object.values(g).map(x => '{' + x.join(',') + '}').join(' ');
    };
    const vars = () => [['연산', st.op || '-'], ['parent', P.map(p => p + 1).join(' ')], ['집합', groups()]];
    rec.push(4, '처음에는 모두 자기 자신이 부모입니다. 원소 8개가 각자 다른 집합입니다.', st, vars());
    const find = x => {
      const path = [x]; while (P[path.at(-1)] !== path.at(-1)) path.push(P[path.at(-1)]);
      const r = path.at(-1); st.hl = path;
      rec.push(path.length > 1 ? 2 : 1, `find(${x + 1}): ${path.map(v => v + 1).join(' → ')}, 루트는 ${r + 1}입니다.`, st, vars());
      const moved = path.slice(0, -2);
      if (moved.length) {
        moved.forEach(v => { P[v] = r; });
        rec.push(2, `경로 압축: ${moved.map(v => v + 1).join(', ')}의 부모를 루트 ${J(r + 1, '으로/로')} 바로 바꿉니다. 다음에는 한 번에 루트에 닿습니다.`, st, vars());
      }
      return r;
    };
    // 체인이 생기기 쉽게: 앞쪽은 서로 다른 집합끼리, 뒤쪽은 무작위
    const ops = [];
    const ids = shuffle(rng, Array.from({ length: n }, (_, i) => i));
    ops.push([ids[0], ids[1]], [ids[2], ids[0]], [ids[3], ids[4]], [ids[5], ids[2]], [ids[3], ids[1]]);
    ops.push([ids[6], ids[rint(rng, 0, 5)]], [ids[1], ids[5]]);
    for (const [a, b] of ops) {
      st.op = `union(${a + 1}, ${b + 1})`; st.hl = [];
      rec.push(5, `${st.op}: ${J(a + 1, '과/와')} ${b + 1}의 루트를 각각 찾습니다.`, st, vars());
      const ra = find(a), rb = find(b);
      if (ra !== rb) { P[rb] = ra; st.hl = [rb, ra]; rec.push(6, `루트가 다르므로(${ra + 1} ≠ ${rb + 1}) ${rb + 1}의 부모를 ${J(ra + 1, '으로/로')} 바꿔 두 집합을 합칩니다.`, st, vars()); }
      else { st.hl = [ra]; rec.push(6, `루트가 ${J(ra + 1, '으로/로')} 같으므로 이미 같은 집합입니다. 아무것도 하지 않습니다.`, st, vars()); }
    }
    st.hl = []; st.op = '';
    rec.push(-1, `연산이 끝났습니다. 남은 집합: ${groups()}`, st, vars());
    return rec.frames;
  },
  draw(f) {
    const s = f.s, P = s.parent, n = P.length;
    const kids = Array.from({ length: n }, () => []);
    const roots = [];
    for (let i = 0; i < n; i++) (P[i] === i ? roots : kids[P[i]]).push(i);
    const leaves = v => kids[v].length ? kids[v].reduce((a, c) => a + leaves(c), 0) : 1;
    const total = roots.reduce((a, r) => a + leaves(r), 0), slot = 480 / total, pos = {};
    let maxD = 0;
    const place = (v, left, d) => {
      const w = leaves(v) * slot; pos[v] = [10 + left + w / 2, 34 + d * 70]; maxD = Math.max(maxD, d);
      let l = left; for (const c of kids[v]) { place(c, l, d + 1); l += leaves(c) * slot; }
    };
    let l = 0; for (const r of roots) { place(r, l, 0); l += leaves(r) * slot; }
    let o = `<defs><marker id="uarr" viewBox="0 0 10 10" refX="9" refY="5" markerUnits="userSpaceOnUse" markerWidth="13" markerHeight="13" orient="auto-start-reverse"><path d="M0,1 L9,5 L0,9 z" class="arrowhead"/></marker></defs>`;
    for (let i = 0; i < n; i++) if (P[i] !== i) {
      const [x1, y1] = pos[i], [x2, y2] = pos[P[i]], dx = x2 - x1, dy = y2 - y1, L = Math.hypot(dx, dy);
      const on = s.hl.includes(i) && s.hl.includes(P[i]);
      o += `<g class="ae${on ? ' try' : ''}"><line x1="${x1 + dx / L * 20}" y1="${y1 + dy / L * 20}" x2="${x2 - dx / L * 24}" y2="${y2 - dy / L * 24}" marker-end="url(#uarr)"/></g>`;
    }
    for (let i = 0; i < n; i++) {
      const [x, y] = pos[i], cls = [P[i] === i ? 'root' : '', s.hl.includes(i) ? 'curn' : ''].join(' ');
      o += `<g class="an ${cls}"><circle cx="${x}" cy="${y}" r="19"/><text x="${x}" y="${y + 5}" text-anchor="middle">${i + 1}</text></g>`;
    }
    const H = Math.max(330, 34 + maxD * 70 + 40);
    return `<svg class="anim-svg" viewBox="0 0 500 ${H}" role="img" aria-label="유니온 파인드 숲">${o}</svg>`;
  },
};

ANIM.mst = {
  name: '최소 신장 트리', title: '크루스칼: 싼 간선부터, 사이클 없이',
  desc: '간선을 가중치가 작은 순으로 보면서, 아직 연결되지 않은 두 정점을 잇는 간선만 고릅니다. 이미 연결된 두 정점을 잇는 간선은 사이클을 만들므로 버립니다. 연결 여부는 유니온 파인드로 판단합니다.',
  code: [
    'edges.sort((a, b) -> a.w - b.w);',
    'for (Edge e : edges) {',
    '    if (find(e.u) != find(e.v)) { // 다른 집합',
    '        union(e.u, e.v);',
    '        mst.add(e); cost += e.w;',
    '    }   // 같은 집합이면 사이클 → 건너뜀',
    '    if (mst.size() == V - 1) break;',
    '}',
  ],
  legend: [['try', '지금 보는 간선'], ['tree', '고른 간선'], ['skip', '사이클이라 버린 간선']],
  gen(rng) {
    const E = makeGraph(rng), order = E.map((_, i) => i).sort((a, b) => E[a].w - E[b].w);
    const st = { E, order, k: -1, pick: [], skip: [], cost: 0 };
    const par = [0, 1, 2, 3, 4, 5, 6], find = x => par[x] === x ? x : (par[x] = find(par[x]));
    const rec = recorder();
    const vars = () => [['간선 순서', order.map(i => {
      const e = E[i], mark = i === st.k ? '→' : st.pick.includes(i) ? '✓' : st.skip.includes(i) ? '✕' : '';
      return `${mark}${GL(e.u)}${GL(e.v)}:${e.w}`;
    }).join(' ')], ['비용 합', st.cost], ['고른 간선', `${st.pick.length} / 6`]];
    rec.push(0, `간선 ${E.length}개를 가중치가 작은 순으로 정렬합니다.`, st, vars());
    for (const i of order) {
      const e = E[i]; st.k = i;
      if (find(e.u) !== find(e.v)) {
        par[find(e.u)] = find(e.v); st.pick.push(i); st.cost += e.w;
        rec.push(4, `${GL(e.u)}–${GL(e.v)} (${e.w}): 아직 서로 연결되지 않은 두 정점이라 고릅니다. 비용 합 ${st.cost}`, st, vars());
      } else {
        st.skip.push(i);
        rec.push(5, `${GL(e.u)}–${GL(e.v)} (${e.w}): 이미 다른 길로 연결돼 있어서, 고르면 사이클이 생깁니다. 건너뜁니다.`, st, vars());
      }
      if (st.pick.length === 6) { st.k = -1; rec.push(6, `정점 7개를 잇는 간선 6개를 모두 골랐습니다. 최소 비용은 ${st.cost}입니다.`, st, vars()); break; }
    }
    return rec.frames;
  },
  draw(f) {
    const s = f.s, used = new Set(s.pick.flatMap(i => [s.E[i].u, s.E[i].v]));
    return graphSvg(GPOS, s.E.map((e, k) => ({ ...e, cls: k === s.k ? 'try' : s.pick.includes(k) ? 'tree' : s.skip.includes(k) ? 'skip' : '' })),
      i => ({ label: GL(i), cls: used.has(i) ? 'front' : '' }));
  },
};

ANIM.topo = {
  name: '위상 정렬', title: '선행 순서를 지키며 줄 세우기',
  desc: '화살표 A → B는 "A를 B보다 먼저"라는 뜻입니다. 들어오는 화살표 수(진입 차수)가 0인 정점, 즉 먼저 끝내야 할 일이 남지 않은 정점부터 꺼냅니다. 꺼낸 정점의 화살표를 지우면 새로 0이 되는 정점이 생깁니다. 정점 위의 숫자가 진입 차수입니다.',
  code: [
    'for (v : 정점) if (indeg[v] == 0) q.add(v);',
    'while (!q.isEmpty()) {',
    '    u = q.poll(); order.add(u);',
    '    for (v : adj[u])',
    '        if (--indeg[v] == 0) q.add(v);',
    '}',
  ],
  legend: [['done', '순서에 들어감'], ['curn', '지금 꺼낸 정점'], ['front', '큐에 있음'], ['try', '지우는 화살표'], ['gone', '지운 화살표']],
  gen(rng) {
    const sizes = [[2, 2, 2, 1], [2, 3, 1, 1], [1, 3, 2, 1], [2, 2, 1, 2]][Math.floor(rng() * 4)];
    const xs = [55, 185, 315, 445], layer = [], pos = [];
    sizes.forEach((k, li) => { for (let j = 0; j < k; j++) { layer.push(li); pos.push([xs[li], k === 1 ? 150 : k === 2 ? 90 + j * 120 : 60 + j * 90]); } });
    const n = pos.length, E = [];
    for (let v = 0; v < n; v++) {
      if (!layer[v]) continue;
      const prevs = [...Array(n).keys()].filter(u => layer[u] === layer[v] - 1);
      const k = Math.min(prevs.length, 1 + (rng() < 0.45 ? 1 : 0));
      for (const u of shuffle(rng, prevs).slice(0, k)) E.push({ u, v, dir: true });
    }
    const labels = shuffle(rng, [...'ABCDEFG']), L = i => labels[i];
    const indeg = Array(n).fill(0); E.forEach(e => indeg[e.v]++);
    const st = { pos, E, labels, indeg, q: [], order: [], cur: -1, edge: -1, gone: [], done: Array(n).fill(false) };
    const rec = recorder();
    const vars = () => [['큐', listText(st.q.map(L))], ['순서', st.order.map(L).join(' → ') || '-']];
    for (let v = 0; v < n; v++) if (!indeg[v]) st.q.push(v);
    rec.push(0, `진입 차수가 0인 ${st.q.map(L).join(', ')}부터 큐에 넣습니다. 먼저 해야 할 일이 없는 정점들입니다.`, st, vars());
    while (st.q.length) {
      const u = st.q.shift(); st.cur = u; st.edge = -1; st.order.push(u); st.done[u] = true;
      rec.push(2, `${J(L(u), '을/를')} 꺼내 순서 맨 뒤에 붙입니다.`, st, vars());
      E.forEach((e, k) => {
        if (e.u !== u) return;
        st.edge = k; st.indeg[e.v]--;
        if (!st.indeg[e.v]) { st.q.push(e.v); rec.push(4, `${L(u)} → ${L(e.v)} 화살표를 지웁니다. ${L(e.v)}의 진입 차수가 0이 되어 큐에 넣습니다.`, st, vars()); }
        else rec.push(4, `${L(u)} → ${L(e.v)} 화살표를 지웁니다. ${J(L(e.v), '은/는')} 아직 ${st.indeg[e.v]}개가 남아 기다립니다.`, st, vars());
        st.gone.push(k); st.edge = -1;
      });
    }
    st.cur = -1;
    rec.push(1, `큐가 비었습니다. 위상 순서는 ${st.order.map(L).join(' → ')}입니다. 모든 화살표가 왼쪽에서 오른쪽으로 향합니다.`, st, vars());
    return rec.frames;
  },
  draw(f) {
    const s = f.s, inQ = new Set(s.q);
    return graphSvg(s.pos, s.E.map((e, k) => ({ ...e, cls: k === s.edge ? 'try' : s.gone.includes(k) ? 'gone' : '' })), i => ({
      label: s.labels[i], cls: [s.done[i] ? 'done' : inQ.has(i) ? 'front' : '', i === s.cur ? 'curn' : ''].join(' '),
      badge: s.done[i] ? null : s.indeg[i],
    }));
  },
};

// 기법 id → 애니메이션 id
const ANIM_OF = { bfs: 'bfs', grid: 'bfs', dfs: 'dfs', backtrack: 'backtrack', dp: 'dp', binsearch: 'binsearch', twoptr: 'twoptr', prefix: 'prefix', pq: 'pq', dijkstra: 'dijkstra', uf: 'uf', mst: 'mst', topo: 'topo' };

// ═══════════════ 플레이어 ═══════════════
const player = { id: null, frames: [], i: 0, timer: null, speed: 800, seed: {} };
try { player.speed = Number(localStorage.getItem('animSpeed')) || 800; } catch (e) {}

const ICON = {
  first: '<path d="M6 5v14M18 6l-8 6 8 6z"/>',
  prev: '<path d="M15 6l-6 6 6 6"/>',
  next: '<path d="M9 6l6 6-6 6"/>',
  play: '<path d="M8 5.5v13l11-6.5z" fill="currentColor"/>',
  pause: '<path d="M8 5v14M16 5v14"/>',
  dice: '<rect x="4" y="4" width="16" height="16" rx="3"/><circle cx="9" cy="9" r="1.3" fill="currentColor"/><circle cx="15" cy="15" r="1.3" fill="currentColor"/><circle cx="15" cy="9" r="1.3" fill="currentColor"/><circle cx="9" cy="15" r="1.3" fill="currentColor"/>',
};
const icon = k => `<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">${ICON[k]}</svg>`;

function stopAnim() { clearInterval(player.timer); player.timer = null; const b = document.getElementById('aplay'); if (b) b.innerHTML = icon('play') + '<span>재생</span>'; }
function animOrder() { return Object.keys(ANIM).sort((a, b) => (techCount[b] || 0) - (techCount[a] || 0)); }

function renderAlgo(id) {
  stopAnim();
  if (!ANIM[id]) id = ANIM[player.id] ? player.id : animOrder()[0];
  player.id = id;
  const def = ANIM[id];
  const mine = techCount[id] ? repCandidates(id) : [];
  app.innerHTML = `
    <div class="page-head"><div><h1>동작 원리</h1>
      <p class="sub">자주 쓴 기법이 어떻게 움직이는지 한 단계씩 보여 줍니다. 입력은 무작위로 만들고, 코드는 그 기법의 일반적인 형태입니다.</p></div></div>
    <div class="rep-pills" id="algoPills">${animOrder().map(a => `<a class="tf" href="#/algo/${a}" aria-pressed="${a === id}">${esc(ANIM[a].name)}${techCount[a] ? `<span class="n">${techCount[a]}</span>` : ''}</a>`).join('')}</div>
    <div class="card">
      <div class="card-h"><h2>${esc(def.name)} · ${esc(def.title)}</h2></div>
      <div class="card-b">
        <p class="anim-desc">${esc(def.desc)}</p>
        <div class="anim">
          <div class="anim-main">
            <div class="stage" id="stage"></div>
            <div class="alegend">${def.legend.map(([k, t]) => `<span><i class="sw" data-k="${k}"></i>${esc(t)}</span>`).join('')}</div>
            <div class="amsg" id="amsg" aria-live="polite"></div>
            <div class="player">
              <button class="btn ib" data-a="first" title="처음으로" aria-label="처음으로">${icon('first')}</button>
              <button class="btn ib" data-a="prev" title="이전 단계 (←)" aria-label="이전 단계">${icon('prev')}</button>
              <button class="btn primary" id="aplay" data-a="play" title="재생/멈춤 (Space)">${icon('play')}<span>재생</span></button>
              <button class="btn ib" data-a="next" title="다음 단계 (→)" aria-label="다음 단계">${icon('next')}</button>
              <input type="range" id="aseek" min="0" value="0" aria-label="단계 이동">
              <span class="pos" id="apos"></span>
              <select id="aspeed" aria-label="속도">
                <option value="1400">느리게</option><option value="800">보통</option><option value="350">빠르게</option><option value="120">아주 빠르게</option>
              </select>
              <button class="btn" data-a="new" title="입력을 새로 만들기">${icon('dice')}<span>새 입력</span></button>
            </div>
          </div>
          <div class="anim-side">
            <div class="pcode" id="pcode">${def.code.map(l => `<div class="pl">${esc(l) || ' '}</div>`).join('')}</div>
            <dl class="avars" id="avars"></dl>
          </div>
        </div>
      </div>
    </div>
    <div class="card" style="margin-top:16px">
      <div class="card-h"><h2>이 기법을 쓴 내 풀이</h2>${mine.length ? '<span class="hint">누르면 해당 기법 줄이 표시된 채로 열림</span>' : ''}</div>
      <div class="card-b">${mine.length ? `<div class="mine">${mine.slice(0, 8).map(p => `<a class="mine-item" href="${href(p)}?t=${id}"><span class="plat">${platName(p)}</span><span class="t">${esc(label(p))}</span><span class="m">${p.loc}줄</span></a>`).join('')}</div>
        ${mine.length > 8 ? `<button class="linkish" id="amineAll" style="margin-top:10px">문제 ${mine.length}개 모두 보기</button>` : ''}` : '<p class="muted" style="margin:0;font-size:14px">아직 이 기법으로 분류된 풀이가 없습니다.</p>'}</div>
    </div>`;

  $('#aspeed').value = String(player.speed);
  if (!$('#aspeed').value) $('#aspeed').value = '800';
  $('#aspeed').addEventListener('change', e => {
    player.speed = Number(e.target.value);
    try { localStorage.setItem('animSpeed', player.speed); } catch (err) {}
    if (player.timer) { stopAnim(); playAnim(); }
  });
  $('#aseek').addEventListener('input', e => { stopAnim(); showFrame(Number(e.target.value)); });
  app.querySelector('.player').addEventListener('click', e => {
    const b = e.target.closest('[data-a]'); if (!b) return;
    const a = b.dataset.a;
    if (a === 'play') togglePlay();
    else if (a === 'first') { stopAnim(); showFrame(0); }
    else if (a === 'prev') stepAnim(-1);
    else if (a === 'next') stepAnim(1);
    else if (a === 'new') { stopAnim(); player.seed[id] = Math.floor(Math.random() * 1e9); buildFrames(); showFrame(0); }
  });
  $('#amineAll')?.addEventListener('click', () => { resetFilters(); state.techs.add(id); location.hash = '#/list'; });
  buildFrames();
  showFrame(0);
}

function buildFrames() {
  const id = player.id;
  if (player.seed[id] == null) player.seed[id] = 20251127;
  player.frames = ANIM[id].gen(mulberry32(player.seed[id]));
  $('#aseek').max = player.frames.length - 1;
}

function showFrame(i) {
  const def = ANIM[player.id], fr = player.frames;
  player.i = Math.max(0, Math.min(fr.length - 1, i));
  const f = fr[player.i];
  $('#stage').innerHTML = def.draw(f);
  document.querySelectorAll('#pcode .pl').forEach((el, k) => el.classList.toggle('on', k === f.line));
  $('#amsg').textContent = f.msg;
  $('#avars').innerHTML = f.vars.map(([k, v]) => `<dt>${esc(k)}</dt><dd>${esc(v)}</dd>`).join('');
  $('#aseek').value = player.i;
  $('#apos').textContent = `${player.i + 1} / ${fr.length}`;
}
function stepAnim(d) { stopAnim(); showFrame(player.i + d); }
function playAnim() {
  if (player.i >= player.frames.length - 1) showFrame(0);
  $('#aplay').innerHTML = icon('pause') + '<span>멈춤</span>';
  player.timer = setInterval(() => {
    if (!document.getElementById('stage')) return stopAnim();
    if (player.i >= player.frames.length - 1) return stopAnim();
    showFrame(player.i + 1);
  }, player.speed);
}
function togglePlay() { player.timer ? stopAnim() : playAnim(); }

addEventListener('keydown', e => {
  if (!location.hash.startsWith('#/algo') || e.target.closest?.('input, select, textarea, button, a') || e.ctrlKey || e.metaKey || e.altKey) return;
  if (e.key === ' ') { e.preventDefault(); togglePlay(); }
  else if (e.key === 'ArrowRight') { e.preventDefault(); stepAnim(1); }
  else if (e.key === 'ArrowLeft') { e.preventDefault(); stepAnim(-1); }
});
