### 解题思路：
一步到位直接按正则表达式的实现来:生成有限状态机

状态机节点设计:

* 该节点字符
* 子孙节点 `map`
* 本节点是否为最终节点
* 本节点的最小长度，如果后面携带 ***，则自由长度为 *0*; 否则为 *1*

### 代码:

```go [-Go]
func debug(v ...interface{}) {
	log.Println(v...)
}

func toString(i interface{}) string {
	switch i.(type) {
	case int:
		return fmt.Sprintf("%v", i)
	case string:
		return fmt.Sprintf("%v", i)
	case bool:
		return fmt.Sprintf("%v", i)
	default:
		return fmt.Sprintf("%p", i)
	}
}

func isMatch(s string, p string) bool {
	begin := new(Node)
	begin.C = '>'
	begin.Size = generatePattern(begin, p, 0)
	debug(begin.String())
	return check(begin, s, 0)
}

type Node struct {
	C        byte
	Parent   *Node
	Children map[byte][]*Node
	End      bool
	Size     int
}

func (n *Node) String() string {
	return n.StringLevel(0, make(map[*Node]bool))
}

func (n *Node) StringLevel(level int, finishNodes map[*Node]bool) string {
	r := make([]string, 0)
	if n.End {
		r = append(r, fmt.Sprintf("  id%s{%v};", toString(n), string(n.C)))
	} else {
		r = append(r, fmt.Sprintf("  id%s(%v);", toString(n), string(n.C)))
	}
	finishNodes[n] = true
	for k, v := range n.Children {
		for _, c := range v {
			if _, ok := finishNodes[c]; !ok {
				r = append(r, c.StringLevel(level+1, finishNodes))
			}
			r = append(r, fmt.Sprintf("  id%s -- %s --> id%s;", toString(n), string(k), toString(c)))
		}
	}
	return strings.Join(r, "\n")
}

func (n *Node) Append(c byte, child *Node) {
	m := n.Children
	if m == nil {
		m = make(map[byte][]*Node)
		n.Children = m
	}
	list := m[c]
	if list == nil {
		list = make([]*Node, 0)
	}
	for _, v := range list {
		if v == child {
			m[c] = list
			return
		}
	}
	list = append(list, child)
	m[c] = list
}

func generatePattern(now *Node, str string, idx int) int {
	if len(str) <= idx {
		now.End = true
		return now.Size
	}
	vnow := now
	switch str[idx] {
	case '*':
		now.Size = 0
		now.Append(now.C, now)
	default:
		node := new(Node)
		node.C = str[idx]
		now.Append(str[idx], node)
		node.Parent = now
		node.Size = 1
		vnow = node
	}
	ret := generatePattern(vnow, str, idx+1)
	if ret == 0 {
		now.End = true
	}
	addParent := now
	for addParent.Parent != nil {
		if addParent.Size == 0 {
			debug(toString(vnow), " -> ", toString(addParent.Parent))
			addParent.Parent.Append(vnow.C, vnow)
			addParent = addParent.Parent
		} else {
			break
		}
	}
	return now.Size + ret
}

func check(now *Node, str string, idx int) bool {
	if len(str) <= idx {
		return now.End
	}
	list := now.Children['.']
	for _, v := range now.Children[str[idx]] {
		list = append(list, v)
	}
	for _, v := range list {
		r := check(v, str, idx+1)
		if r {
			return true
		}
	}
	return false
}
```

生成的有限状态机图, 使用 `mermaid` 去查看。如下用例生成的状态机 `c*..b*a*a.*a..*c` 与 `mis*is*`

![Screen Shot 2019-06-01 at 13.41.41.png](https://pic.leetcode-cn.com/f4d2b6427ee8f13929f229c39d28b23c0516e97d3b78f2ef1e0264433effc287-Screen%20Shot%202019-06-01%20at%2013.41.41.png)

```mermaid
graph LR;
  id0xc4200809c0(>);
  id0xc4200809f0(c);
  id0xc4200809f0 -- c --> id0xc4200809f0;
  id0xc420080a80(.);
  id0xc420080ab0(.);
  id0xc420080b10(b);
  id0xc420080b10 -- b --> id0xc420080b10;
  id0xc420080ba0(a);
  id0xc420080ba0 -- a --> id0xc420080ba0;
  id0xc420080c00(a);
  id0xc420080c30(.);
  id0xc420080c30 -- . --> id0xc420080c30;
  id0xc420080cc0(a);
  id0xc420080cf0(.);
  id0xc420080d50(.);
  id0xc420080d50 -- . --> id0xc420080d50;
  id0xc420080de0{c};
  id0xc420080d50 -- c --> id0xc420080de0;
  id0xc420080cf0 -- . --> id0xc420080d50;
  id0xc420080cf0 -- c --> id0xc420080de0;
  id0xc420080cc0 -- . --> id0xc420080cf0;
  id0xc420080c30 -- a --> id0xc420080cc0;
  id0xc420080c00 -- . --> id0xc420080c30;
  id0xc420080c00 -- a --> id0xc420080cc0;
  id0xc420080ba0 -- a --> id0xc420080c00;
  id0xc420080b10 -- a --> id0xc420080ba0;
  id0xc420080b10 -- a --> id0xc420080c00;
  id0xc420080ab0 -- b --> id0xc420080b10;
  id0xc420080ab0 -- a --> id0xc420080c00;
  id0xc420080ab0 -- a --> id0xc420080ba0;
  id0xc420080a80 -- . --> id0xc420080ab0;
  id0xc4200809f0 -- . --> id0xc420080a80;
  id0xc4200809c0 -- c --> id0xc4200809f0;
  id0xc4200809c0 -- . --> id0xc420080a80;

    id0xc420080780(>);
  id0xc4200807b0(m);
  id0xc420080810(i);
  id0xc420080870(s);
  id0xc420080870 -- s --> id0xc420080870;
  id0xc420080900{i};
  id0xc420080930{s};
  id0xc420080930 -- s --> id0xc420080930;
  id0xc420080900 -- s --> id0xc420080930;
  id0xc420080870 -- i --> id0xc420080900;
  id0xc420080810 -- s --> id0xc420080870;
  id0xc420080810 -- i --> id0xc420080900;
  id0xc4200807b0 -- i --> id0xc420080810;
  id0xc420080780 -- m --> id0xc4200807b0;
```

