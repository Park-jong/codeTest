// 코딩테스트 풀이 시각화 사이트 생성기
// 사용법: node tools/build-site.mjs   →  site/index.html 생성
// 외부 의존성 없음. untitled/src 의 .java 파일과 git 기록을 분석해 한 장짜리 HTML로 묶는다.

import { readFileSync, writeFileSync, readdirSync, mkdirSync } from 'node:fs';
import { execFileSync } from 'node:child_process';
import { join, dirname } from 'node:path';
import { fileURLToPath } from 'node:url';

const ROOT = join(dirname(fileURLToPath(import.meta.url)), '..');
const SRC = join(ROOT, 'untitled', 'src');
const OUT_DIR = join(ROOT, 'site');
const TEMPLATE = join(ROOT, 'tools', 'site-template.html');

// ───────────────────────── 기법(태그) 정의 ─────────────────────────
// id → 표시 이름, 묶음(group)
const TECH = {
  bfs:       ['BFS', '탐색'],
  dfs:       ['재귀 · DFS', '탐색'],
  backtrack: ['백트래킹', '탐색'],
  brute:     ['브루트포스', '탐색'],
  grid:      ['격자 이동(dx/dy)', '탐색'],
  dp:        ['DP', 'DP'],
  greedy:    ['그리디', '전략'],
  binsearch: ['이분 탐색', '전략'],
  twoptr:    ['투 포인터', '전략'],
  sort:      ['정렬', '전략'],
  prefix:    ['누적합', '전략'],
  bitmask:   ['비트마스킹', '전략'],
  adhoc:     ['애드혹', '전략'],
  simulation:['구현 · 시뮬레이션', '전략'],
  graph:     ['그래프', '그래프'],
  adj:       ['인접 리스트', '그래프'],
  tree:      ['트리', '그래프'],
  dijkstra:  ['다익스트라', '그래프'],
  floyd:     ['플로이드-워셜', '그래프'],
  uf:        ['유니온 파인드', '그래프'],
  mst:       ['최소 신장 트리', '그래프'],
  topo:      ['위상 정렬', '그래프'],
  flow:      ['네트워크 플로우', '그래프'],
  sparse:    ['희소 테이블 · LCA', '그래프'],
  pq:        ['우선순위 큐', '자료구조'],
  stack:     ['스택', '자료구조'],
  hash:      ['해시(Map/Set)', '자료구조'],
  treemap:   ['TreeMap/TreeSet', '자료구조'],
  segtree:   ['세그먼트 트리 · 펜윅', '자료구조'],
  kmp:       ['KMP', '문자열'],
  math:      ['모듈러 · 정수론', '수학'],
  minimax:   ['미니맥스', '탐색'],
};

// 파일명/커밋 메시지에 쓰인 태그 → 기법 id
const NAME_TAG = {
  dp: 'dp', greedy: 'greedy', graph: 'graph', bfs: 'bfs', dfs: 'dfs', kmp: 'kmp',
  tree: 'tree', binarysearch: 'binsearch', twopointer: 'twoptr', adhoc: 'adhoc',
  bitmasking: 'bitmask', bitmask: 'bitmask', minimax: 'minimax',
  '브루트포스': 'brute', '완전탐색': 'brute', '플로이드워셜': 'floyd', '다익스트라': 'dijkstra',
  '누적합': 'prefix', '최대유량': 'flow', '그리디': 'greedy', '구현': 'simulation',
  '시뮬레이션': 'simulation', '백트래킹': 'backtrack', '이분탐색': 'binsearch',
};

// ───────────────────────── 소스 전처리 ─────────────────────────
// 주석과 문자열 내용을 공백으로 지워 분석용 코드를 만든다 (줄 번호 유지)
function strip(src) {
  let out = '';
  let i = 0;
  const n = src.length;
  while (i < n) {
    const c = src[i], d = src[i + 1];
    if (c === '/' && d === '/') {
      while (i < n && src[i] !== '\n') { out += ' '; i++; }
    } else if (c === '/' && d === '*') {
      while (i < n && !(src[i] === '*' && src[i + 1] === '/')) { out += src[i] === '\n' ? '\n' : ' '; i++; }
      out += '  '; i += 2;
    } else if (c === '"' || c === "'") {
      const q = c; out += q; i++;
      while (i < n && src[i] !== q && src[i] !== '\n') {
        if (src[i] === '\\') { out += ' '; i++; }
        out += ' '; i++;
      }
      if (i < n) { out += src[i]; i++; }
    } else { out += c; i++; }
  }
  return out;
}

function lineOf(text, idx) {
  let line = 1;
  for (let i = 0; i < idx; i++) if (text.charCodeAt(i) === 10) line++;
  return line;
}

function matchBrace(text, openIdx) {
  let depth = 0;
  for (let i = openIdx; i < text.length; i++) {
    if (text[i] === '{') depth++;
    else if (text[i] === '}') { depth--; if (depth === 0) return i; }
  }
  return text.length - 1;
}

const KEYWORDS = new Set(['if', 'for', 'while', 'switch', 'catch', 'return', 'new', 'else', 'do',
  'try', 'synchronized', 'throw', 'case', 'super', 'this']);

// ───────────────────────── 구조 분석 ─────────────────────────
function analyzeStructure(code) {
  // 클래스
  const classes = [];
  const classRe = /\b(class|record|enum|interface)\s+(\w+)[^{;]*\{/g;
  let m;
  while ((m = classRe.exec(code))) {
    const open = m.index + m[0].length - 1;
    const close = matchBrace(code, open);
    classes.push({ kind: m[1], name: m[2], start: m.index, open, end: close,
      line: lineOf(code, m.index), endLine: lineOf(code, close) });
  }

  // 메서드
  const methods = [];
  const methRe = /(^|[\n;{}])([ \t]*)((?:(?:public|private|protected|static|final|synchronized|abstract)\s+)*)([\w$<>\[\],.?]+(?:\s*<[^>]*>)?(?:\s*\[\s*\])*)\s+(\w+)\s*\(([^()]*)\)\s*(?:throws\s+[\w.,\s]+?)?\s*\{/g;
  while ((m = methRe.exec(code))) {
    const ret = m[4], name = m[5];
    if (KEYWORDS.has(name) || KEYWORDS.has(ret) || ret === 'else') continue;
    const declIdx = m.index + m[1].length + m[2].length;
    const open = m.index + m[0].length - 1;
    const close = matchBrace(code, open);
    methods.push({ name, ret, params: m[6].trim().replace(/\s+/g, ' '),
      start: declIdx, open, end: close,
      line: lineOf(code, declIdx), endLine: lineOf(code, close) });
    methRe.lastIndex = open + 1;
  }
  // 생성자: 클래스명(…) {
  for (const cl of classes) {
    const ctorRe = new RegExp(`(^|[\\n;{}])[ \\t]*(?:(?:public|private|protected)\\s+)?${cl.name}\\s*\\(([^()]*)\\)\\s*\\{`, 'g');
    while ((m = ctorRe.exec(code))) {
      const declIdx = m.index + m[1].length;
      const open = m.index + m[0].length - 1;
      if (declIdx < cl.open || declIdx > cl.end) continue;
      methods.push({ name: cl.name, ret: '', ctor: true, params: m[2].trim(), start: declIdx, open,
        end: matchBrace(code, open), line: lineOf(code, declIdx), endLine: lineOf(code, matchBrace(code, open)) });
    }
  }

  // 다른 메서드 안에 들어 있는 것(익명 클래스·지역 클래스 등)은 제외
  const top = methods.filter(a => !methods.some(b => b !== a && b.open < a.start && a.end < b.end));
  // 소속 클래스: 가장 안쪽 클래스
  for (const mt of top) {
    const owners = classes.filter(c => c.open < mt.start && mt.end <= c.end);
    owners.sort((a, b) => (a.end - a.open) - (b.end - b.open));
    mt.owner = owners[0]?.name ?? null;
  }
  top.sort((a, b) => a.start - b.start);
  return { classes, methods: top };
}

function analyzeCalls(code, methods, mainClass) {
  const own = methods.filter(m => m.owner === mainClass && !m.ctor);
  const names = new Set(own.map(m => m.name));
  const edges = new Map(); // "a>b" → {from,to,lines}
  for (const mt of own) {
    const body = code.slice(mt.open, mt.end);
    const re = /(\.?)\b(\w+)\s*\(/g;
    let m;
    while ((m = re.exec(body))) {
      const name = m[2];
      if (!names.has(name)) continue;
      if (m[1] === '.') {
        const before = body.slice(Math.max(0, m.index - 4), m.index);
        if (before !== 'this') continue;
      }
      const prev = body.slice(Math.max(0, m.index - 4), m.index);
      if (/new\s$/.test(prev)) continue;
      const key = `${mt.name}>${name}`;
      if (!edges.has(key)) edges.set(key, { from: mt.name, to: name, lines: [] });
      const ln = lineOf(code, mt.open + m.index);
      const e = edges.get(key);
      if (!e.lines.includes(ln)) e.lines.push(ln);
    }
  }
  return [...edges.values()];
}

function maxLoopDepth(code) {
  // for/while/do 다음에 여는 { 를 반복 블록으로 본다
  const stack = [];
  let pendingLoop = 0, max = 0, depth = 0;
  const re = /\b(for|while|do)\b|[{}]|;/g;
  let m, parenSkip = -1;
  while ((m = re.exec(code))) {
    if (m.index < parenSkip) continue;
    const t = m[0];
    if (t === 'for' || t === 'while' || t === 'do') {
      // do-while 의 while(...); 은 루프 시작이 아님
      if (t === 'while') {
        const after = code.slice(m.index);
        const pm = /^while\s*\(/.exec(after);
        if (pm) {
          let d = 0, j = m.index + pm[0].length - 1;
          for (; j < code.length; j++) { if (code[j] === '(') d++; else if (code[j] === ')') { d--; if (d === 0) break; } }
          const rest = code.slice(j + 1).match(/^\s*(.)/);
          parenSkip = j + 1;
          if (rest && rest[1] === ';') continue;
        }
      } else if (t === 'for') {
        const pm = /^for\s*\(/.exec(code.slice(m.index));
        if (pm) {
          let d = 0, j = m.index + pm[0].length - 1;
          for (; j < code.length; j++) { if (code[j] === '(') d++; else if (code[j] === ')') { d--; if (d === 0) break; } }
          parenSkip = j + 1;
        }
      }
      pendingLoop++;
    } else if (t === '{') {
      const isLoop = pendingLoop > 0;
      if (isLoop) { pendingLoop = 0; depth++; max = Math.max(max, depth); }
      stack.push(isLoop);
    } else if (t === '}') {
      if (stack.pop()) depth--;
    } else if (t === ';') {
      // 중괄호 없는 한 줄 반복문
      if (pendingLoop > 0) { max = Math.max(max, depth + pendingLoop); pendingLoop = 0; }
    }
  }
  return max;
}

// ───────────────────────── 기법 감지 ─────────────────────────
function detect(code, lines, struct, calls) {
  const found = {}; // id → Set(lines)
  const hit = (id, lns) => {
    if (!found[id]) found[id] = new Set();
    for (const l of lns) found[id].add(l);
  };
  const grep = (re) => {
    const out = [];
    lines.forEach((l, i) => { if (re.test(l)) out.push(i + 1); });
    return out;
  };
  const has = (re) => re.test(code);

  const recursive = calls.filter(e => e.from === e.to);
  if (recursive.length) hit('dfs', recursive.flatMap(e => e.lines));

  if (has(/new\s+(ArrayDeque|LinkedList)\s*</) && has(/\.(poll|pollFirst|pollLast)\s*\(\s*\)/) && has(/isEmpty\s*\(\s*\)/)) {
    const queueNames = [...code.matchAll(/(?:Queue|Deque|ArrayDeque|LinkedList)\s*<[^>]*>\s+(\w+)\s*=\s*new\s+(?:ArrayDeque|LinkedList)/g)].map(x => x[1]);
    if (queueNames.length) {
      const re = new RegExp(`\\b(${queueNames.join('|')})\\.(poll|pollFirst|offer|add|addLast)\\s*\\(`);
      const l = grep(re);
      if (l.length) hit('bfs', l);
    }
  }
  if (recursive.length) {
    const l = grep(/\b(visit\w*|isVisited|used|check\w*|selected|chosen)\s*(\[[^\]]*\])+\s*=\s*false/);
    const l2 = grep(/\.remove\s*\(\s*\w+\.size\(\)\s*-\s*1\s*\)|\.removeLast\s*\(\s*\)/);
    if (l.length || l2.length) hit('backtrack', [...l, ...l2]);
  }
  { const l = grep(/\b\w*(dp|Dp|DP|memo|Memo|cache|Cache)\w*\s*\[/); if (l.length >= 2) hit('dp', l); }
  if (has(/PriorityQueue/)) hit('pq', grep(/PriorityQueue/));
  if (has(/PriorityQueue/) && has(/\b(dist|distance|minDist|cost)\w*\s*\[/i)) {
    const l = grep(/\b(dist|distance|minDist|cost)\w*\s*\[[^\]]*\]\s*(\[[^\]]*\])*\s*(>|<|=)[^=]/i);
    if (l.length) hit('dijkstra', l);
  }
  const mnames = struct.methods.map(m => m.name);
  if (mnames.some(n => /^(find\w*|get\w*Parent|root)$/i.test(n)) && mnames.some(n => /^(union\w*|merge|unite|join)$/i.test(n))) {
    hit('uf', struct.methods.filter(m => /^(find\w*|get\w*Parent|root|union\w*|merge|unite|join)$/i.test(m.name)).map(m => m.line));
    if (has(/(Arrays|Collections)\.sort|\.sort\s*\(|PriorityQueue/)) hit('mst', grep(/(Arrays|Collections)\.sort|\.sort\s*\(/));
  }
  {
    const l = grep(/\bmid\w*\s*=\s*\(?\s*\w+\s*\+\s*\w+\s*\)?\s*(\/\s*2|>>\s*1|>>>\s*1)|\bmid\w*\s*=\s*\w+\s*\+\s*\(\s*\w+\s*-\s*\w+\s*\)\s*\/\s*2|(Arrays|Collections)\.binarySearch/);
    if (l.length) hit('binsearch', l);
    if (mnames.some(n => /^(lower|upper)_?bound$/i.test(n))) hit('binsearch', struct.methods.filter(m => /bound$/i.test(m.name)).map(m => m.line));
  }
  if (!found.binsearch) {
    const a = grep(/\b(left|start|lo|low|l|s|front)\s*(\+\+|\+=\s*1)/);
    const b = grep(/\b(right|end|hi|high|r|e|back)\s*(\+\+|--|\+=\s*1|-=\s*1)/);
    if (a.length && b.length && has(/while\s*\(/)) hit('twoptr', [...a, ...b]);
  }
  if (has(/(Arrays|Collections)\.sort|\.sort\s*\(/)) hit('sort', grep(/(Arrays|Collections)\.sort|\.sort\s*\(/));
  {
    const l = grep(/1\s*<<\s*[\w(]|<<\s*1\b.*&|&\s*\(\s*1\s*<</);
    const bitAnd = grep(/[\w\])]\s*&\s*[\w(]/);
    if (l.length && (bitAnd.length || has(/\bmask\b/i) || has(/\|=?\s*\(?\s*1\s*<</))) hit('bitmask', [...l, ...bitAnd]);
  }
  {
    const l = grep(/\b(?!dp)(\w+)\s*\[\s*(\w+)\s*\]\s*=\s*\1\s*\[\s*\2\s*-\s*1\s*\]\s*\+/);
    const l2 = grep(/\b(sum|prefix|psum|acc|pre)\w*\s*\[/i);
    if (l.length || (l2.length >= 2 && has(/\[\s*\w+\s*-\s*1\s*\]/))) hit('prefix', [...l, ...l2]);
  }
  {
    const l = grep(/\b(\w+)\s*&\s*-\s*\1\b|\b(\w+)\s*\+=\s*\2\s*&\s*-\2|\bnode\s*\*\s*2\b|\b(\w+)\s*<<\s*1\s*\|\s*1|tree\w*\s*\[\s*\w+\s*\*\s*2/);
    if (l.length) hit('segtree', l);
  }
  { const l = grep(/=\s*(fail|pi|table|lps|failure)\w*\s*\[\s*\w+\s*-\s*1\s*\]/); if (l.length) hit('kmp', l); }
  { const l = grep(/\b(dx|dy|dr|dc|drow|dcol|dir\w*|direction\w*|move[XYRC]\w*|delta\w*)\s*(\[\s*\])*\s*=\s*(new\s+int\s*(\[\s*\])+\s*)?\{/i); if (l.length) hit('grid', l); }
  {
    const l = grep(/(List|ArrayList|LinkedList)\s*<[^>]*>\s*\[\s*\]|new\s+ArrayList\s*\[|List\s*<\s*List\s*<|Map\s*<\s*\w+\s*,\s*(Array)?List\s*<|List\s*<\s*\w+\s*>\s+(next|nexts|children|child|adj\w*|edges?|links?|neighbou?rs?|graph|connect\w*|road\w*)\b/);
    if (l.length) hit('adj', l);
  }
  if (has(/Hash(Map|Set)/)) hit('hash', grep(/Hash(Map|Set)/));
  if (has(/Tree(Map|Set)/)) hit('treemap', grep(/Tree(Map|Set)/));
  if (has(/\bStack\s*</) || (has(/\.push\s*\(/) && has(/\.pop\s*\(\s*\)/))) hit('stack', grep(/\bStack\s*<|\.push\s*\(|\.pop\s*\(\s*\)/));
  { const l = grep(/\b(in|)deg(ree)?\w*\s*\[/i); if (l.length && has(/indeg|inDeg|degree/i)) hit('topo', l); }
  { const l = grep(/(\w+)\s*\[\s*(\w+)\s*\]\s*\[\s*(\w+)\s*\]\s*\+\s*\1\s*\[\s*\3\s*\]\s*\[/); if (l.length) hit('floyd', l); }
  { const l = grep(/\b(capacity|cap|flow|residual)s?\s*\[/i); if (l.length >= 2) hit('flow', l); }
  { const l = grep(/=\s*(\w+)\s*\[[^\]]+\]\s*\[\s*\1\s*\[/); if (l.length) hit('sparse', l); }
  { const l = grep(/\bgcd\s*\(|1_?000_?000_?007|\bMOD\b|998_?244_?353/); if (l.length) hit('math', l); }

  const out = {};
  for (const [k, v] of Object.entries(found)) out[k] = [...v].sort((a, b) => a - b);
  return out;
}

// ───────────────────────── 파일명 파싱 ─────────────────────────
function parseName(file) {
  const base = file.replace(/\.java$/, '');
  const parts = base.split('_');
  if (parts[0] === 'Main' && parts.length === 1) return null; // 빈 Main.java
  if (parts[0] === 'Solution') {
    return { platform: 'programmers', num: null, title: parts.slice(1).join(' '), tags: [] };
  }
  const num = /^\d+$/.test(parts[1]) ? Number(parts[1]) : null;
  const rest = parts.slice(num != null ? 2 : 1);
  const title = rest.pop() ?? base;
  return { platform: 'boj', num, title, tags: rest };
}

function tagIds(tokens) {
  const ids = [], extra = [];
  for (const raw of tokens) {
    const t = raw.trim();
    if (!t) continue;
    const id = NAME_TAG[t.toLowerCase()] ?? NAME_TAG[t];
    if (id) ids.push(id); else extra.push(t);
  }
  return { ids, extra };
}

// ───────────────────────── git 기록 ─────────────────────────
function gitHistory() {
  const map = new Map(); // path → {first, last, msg, commits}
  let raw = '';
  try {
    raw = execFileSync('git', ['-c', 'core.quotepath=false', 'log', '--reverse', '--name-only',
      '--format=@@%h|%ad|%s', '--date=short', '--', 'untitled/src'], { cwd: ROOT, encoding: 'utf8', maxBuffer: 64 << 20 });
  } catch { return map; }
  let cur = null;
  for (const line of raw.split('\n')) {
    if (line.startsWith('@@')) {
      const [hash, date, ...msg] = line.slice(2).split('|');
      cur = { hash, date, msg: msg.join('|') };
    } else if (line.trim() && cur) {
      const f = line.trim().split('/').pop();
      const e = map.get(f);
      if (!e) map.set(f, { first: cur.date, last: cur.date, msg: cur.msg, commits: 1 });
      else { e.last = cur.date; e.commits++; }
    }
  }
  return map;
}

// ───────────────────────── 실행 ─────────────────────────
const history = gitHistory();
const files = readdirSync(SRC).filter(f => f.endsWith('.java')).sort();
const problems = [];

for (const file of files) {
  const meta = parseName(file);
  if (!meta) continue;
  const src = readFileSync(join(SRC, file), 'utf8').replace(/\r\n?/g, '\n');
  if (!src.trim()) continue;
  const code = strip(src);
  const codeLines = code.split('\n');
  const struct = analyzeStructure(code);
  const mainClassName = file.replace(/\.java$/, '');
  const mainClass = struct.classes.find(c => c.name === mainClassName)?.name
    ?? struct.classes.find(c => /^(Main|Solution)/.test(c.name))?.name
    ?? struct.classes[0]?.name;
  const calls = analyzeCalls(code, struct.methods, mainClass);
  const detected = detect(code, codeLines, struct, calls);

  const h = history.get(file) ?? {};
  const msgTokens = (h.msg ?? '').split(/[\s_+(),·\/]+/);
  const fromName = tagIds(meta.tags);
  const fromMsg = tagIds(msgTokens).ids;
  const explicit = [...new Set([...fromName.ids, ...fromMsg])];
  // 파일명 태그는 있는데 코드에서 못 찾은 경우에도 목록에 남긴다
  const techs = [...new Set([...explicit, ...Object.keys(detected)])];

  const nonEmpty = src.split('\n').filter(l => l.trim()).length;
  const io = [];
  if (/BufferedReader/.test(code)) io.push('BufferedReader');
  if (/\bScanner\b/.test(code)) io.push('Scanner');
  if (/BufferedWriter/.test(code)) io.push('BufferedWriter');
  if (/StringBuilder/.test(code)) io.push('StringBuilder');

  const fields = [];
  const mc = struct.classes.find(c => c.name === mainClass);
  if (mc) {
    // 메인 클래스 직속 필드 (메서드/내부 클래스 바깥)
    const inner = [...struct.methods.filter(m => m.owner === mainClass), ...struct.classes.filter(c => c !== mc && c.start > mc.open && c.end < mc.end)];
    const body = code.slice(mc.open + 1, mc.end);
    const fre = /(?:^|[;{}\n])[ \t]*((?:(?:static|final|private|public|protected)\s+)*)([\w<>\[\],.?]+(?:\s*\[\s*\])*)\s+(\w+)\s*(=[^;]*)?;/g;
    let fm;
    while ((fm = fre.exec(body))) {
      const abs = mc.open + 1 + fm.index;
      if (inner.some(x => x.start <= abs && abs <= x.end)) continue;
      if (['return', 'else', 'new', 'throw', 'import', 'package'].includes(fm[2])) continue;
      fields.push({ type: fm[2].replace(/\s+/g, ''), name: fm[3], line: lineOf(code, abs + (fm[0].length - fm[0].trimStart().length)) });
    }
  }

  problems.push({
    id: mainClassName,
    file,
    platform: meta.platform,
    num: meta.num,
    title: meta.title,
    extraTags: fromName.extra,
    explicit,
    techs,
    evidence: detected,
    first: h.first ?? null,
    last: h.last ?? null,
    commits: h.commits ?? 0,
    commitMsg: h.msg ?? '',
    loc: nonEmpty,
    lines: src.split('\n').length,
    loopDepth: maxLoopDepth(code),
    io,
    mainClass,
    methods: struct.methods.map(m => ({ name: m.name, ret: m.ret, params: m.params, line: m.line, endLine: m.endLine,
      owner: m.owner, ctor: !!m.ctor, size: m.endLine - m.line + 1 })),
    classes: struct.classes.filter(c => c.name !== mainClass).map(c => ({ kind: c.kind, name: c.name, line: c.line, endLine: c.endLine })),
    calls,
    fields,
    source: src,
  });
}

const data = { generatedAt: new Date().toISOString().slice(0, 10), tech: TECH, problems };
const BS = String.fromCharCode(92); // 백슬래시
const json = JSON.stringify(data).replace(new RegExp("[<" + String.fromCharCode(0x2028, 0x2029) + "]", "g"), c => BS + "u" + c.charCodeAt(0).toString(16).padStart(4, "0"));
const html = readFileSync(TEMPLATE, 'utf8').replace('/*__DATA__*/null', () => json);
mkdirSync(OUT_DIR, { recursive: true });
writeFileSync(join(OUT_DIR, 'index.html'), html);

// 요약 출력
const count = {};
for (const p of problems) for (const t of p.techs) count[t] = (count[t] ?? 0) + 1;
console.log(`문제 ${problems.length}개 분석 → site/index.html`);
console.log(Object.entries(count).sort((a, b) => b[1] - a[1]).map(([k, v]) => `${TECH[k]?.[0] ?? k} ${v}`).join(' · '));
