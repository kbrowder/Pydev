/*
 * Created on Feb 11, 2006
 */
package org.python.pydev.parser.prettyprinter;

import org.python.pydev.core.IGrammarVersionProvider;
import org.python.pydev.core.IPythonNature;
import org.python.pydev.parser.jython.SimpleNode;
import org.python.pydev.parser.jython.ast.Module;

public class PrettyPrinterTest extends AbstractPrettyPrinterTestBase{

    public static void main(String[] args) {
        try {
            DEBUG = true;
            PrettyPrinterTest test = new PrettyPrinterTest();
            test.setUp();
            test.testIfElse3();
            test.tearDown();
            System.out.println("Finished");
            junit.textui.TestRunner.run(PrettyPrinterTest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void testNewIf() throws Exception {
        String s = "" +
        "j = stop if (arg in gets) else start\n"+
        "";
        for(int i=IGrammarVersionProvider.GRAMMAR_PYTHON_VERSION_2_5;i<=IGrammarVersionProvider.LATEST_GRAMMAR_VERSION;i++){
            checkPrettyPrintEqual(s);
        }
    }
    
    
    public void testMethodDef() throws Exception {
        String s = "" +
        "def _dump_registry(cls,file=None):\n" +
        "    pass\n" +
        "";
        for(int i=IGrammarVersionProvider.GRAMMAR_PYTHON_VERSION_2_4;i<=IGrammarVersionProvider.LATEST_GRAMMAR_VERSION;i++){
            //try with all the grammars
            setDefaultVersion(i);
            checkPrettyPrintEqual(s);
        }
    }
    
    public void testExec2() throws Exception {
        String s = ""+
        "exec ('a=1')\n" +
        "";
        for(int i=IGrammarVersionProvider.GRAMMAR_PYTHON_VERSION_2_4;i<=IGrammarVersionProvider.LATEST_GRAMMAR_VERSION;i++){
            //try with all the grammars
            setDefaultVersion(i);
            checkPrettyPrintEqual(s);
        }
    }

    

    public void testMethodDef4() throws Exception {
        String s = "" +
        "def _set_stopinfo(stoplineno=-1):\n" +
        "    pass\n" +
        "";
        for(int i=IGrammarVersionProvider.GRAMMAR_PYTHON_VERSION_2_4;i<=IGrammarVersionProvider.LATEST_GRAMMAR_VERSION;i++){
            //try with all the grammars
            setDefaultVersion(i);
            checkPrettyPrintEqual(s);
        }
    }
    
    
    public void testMethodDef2() throws Exception {
        String s = "" +
        "def _set_stopinfo(self,stopframe,returnframe,stoplineno=-1):\n" +
        "    if not sys.args[:1]:\n" +
        "        pass\n" +
        "";
        for(int i=IGrammarVersionProvider.GRAMMAR_PYTHON_VERSION_2_4;i<=IGrammarVersionProvider.LATEST_GRAMMAR_VERSION;i++){
            //try with all the grammars
            setDefaultVersion(i);
            checkPrettyPrintEqual(s);
        }
    }
    
    
    public void testMethodDef5() throws Exception {
        String s = "" +
        "def _set_stopinfo(stoplineno=not x[-1]):\n" +
        "    if not sys.args[:1]:\n" +
        "        pass\n" +
        "";
        for(int i=IGrammarVersionProvider.GRAMMAR_PYTHON_VERSION_2_4;i<=IGrammarVersionProvider.LATEST_GRAMMAR_VERSION;i++){
            //try with all the grammars
            setDefaultVersion(i);
            checkPrettyPrintEqual(s);
        }
    }
    
    public void testMethodDef3() throws Exception {
        String s = "" +
        "def Bastion(object,filter=lambda name:name[:1] != '_',name=None,bastionclass=BastionClass):\n" +
        "    pass\n" +
        "";
        for(int i=IGrammarVersionProvider.GRAMMAR_PYTHON_VERSION_2_4;i<=IGrammarVersionProvider.LATEST_GRAMMAR_VERSION;i++){
            //try with all the grammars
            setDefaultVersion(i);
            checkPrettyPrintEqual(s);
        }
    }

    public void testListComp5() throws Exception {
        String s = 
            "data = [[1,2,3],[4,5,6]]\n" +
            "newdata = [[val * 2 for val in lst] for lst in data]\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testStrings() throws Exception {
        String s = "" +
        "\"test\"\n" +
        "'test'\n" +
        "'''test'''\n" +
        "u'''test'''\n" +
        "b'''test'''\n" +
        "r'''test'''\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testNums() throws Exception {
        String s = "" +
        "0o700\n" +
        "0O700\n" +
        "0700\n" +
        "0x700\n" +
        "0X700\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testClassDecorator() throws Exception {
        String s = "" +
        "@classdec\n" +
        "@classdec2\n" +
        "class A:\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testFuncCallWithListComp() throws Exception {
        String str = "" +
        "any(cls.__subclasscheck__(c) for c in [subclass,subtype])\n" +
        "";
        checkPrettyPrintEqual(str);
    }
    
    public void testNewFuncCall() throws Exception {
        String s = "Call(1,2,3,*(4,5,6),keyword=13,**kwargs)\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testExceptAs() throws Exception {
        String s = "" +
        "try:\n" +
        "    a = 10\n" +
        "except RuntimeError as x:\n" +
        "    print x\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testIfs() throws Exception {
        String s = "" +
                "def method1():\n" +
                "    if idx > 2:\n" +
                "        print ''\n" +
                "    else:\n" +
                "        print ''\n" +
                "    if idx == 5:\n" +
                "        print 'nothing!'\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testTryFinallyBeginNode() throws Exception {
        doTryFinallyBeginNode(IPythonNature.GRAMMAR_PYTHON_VERSION_2_4);
        doTryFinallyBeginNode(IPythonNature.GRAMMAR_PYTHON_VERSION_2_5);
    }
    
    public void doTryFinallyBeginNode(int version) throws Exception {
        String str = "" +
                "try:\n" +
                "    a = 1\n" +
                "finally:\n" +
                "    pass\n" +
                "";
        SimpleNode node = checkPrettyPrintEqual(str);
        Module m = (Module) node;
        SimpleNode f = (SimpleNode) m.body[0];
        assertEquals(1, f.beginLine);
    }
    
    public void testComments() throws Exception {
        String str = "" +
                "class MyMeta(type):\n" +
                "    def __str__(cls):\n" +
                "        return \"Beautiful class '%s'\" % cls.__name__\n" +
                "class MyClass:\n" +
                "    __metaclass__ = MyMeta\n" +
                "print type(foox)\n" +
                "# after print type\n" +
                "class A(object):# on-line\n" +
                "    # foo test\n" +
                "    def met(self):\n" +
                "        print 'A'\n" +
                "";
        checkPrettyPrintEqual(str);
        
    }
    
    public void testComment5() throws Exception {
        String str = "" +
                "class CoolApproach(object):\n" +
                "    # this tests also a tuple \"special case\"\n" +
                "    def foodeco(**arg5):\n" +
                "        pass\n" +
                "";
        checkPrettyPrintEqual(str);
        
    }
    
    public void testDecoration() throws Exception {
        String str = "" +
                "class Foo:\n" +
                "    @foodeco(('arg_3',),2,a=2,b=3)\n" +
                "    def __init__(self,arg_1,(arg_2,arg_3),arg_4,arg_5):\n" +
                "        pass\n" +
                "";
        checkPrettyPrintEqual(str);
    }
    
    public void testComments6() throws Exception {
        String str = "" +
                "class FooApproach(CoolApproach):\n" +
                "    def __init__(self,arg_1,(arg_2,arg_3),*arg_4,**arg_5):\n" +
                "        # .. at this point all parameters except for 'arg_3' have been\n" +
                "        # copied to object attributes\n" +
                "        pass\n" +
                "";
        checkPrettyPrintEqual(str);
        
    }
    
    public void testComprehension() throws Exception {
        String str = "compre4list = [zahl ** 2 for zahl in (1,4,6) if zahl % 2 == 1 if zahl % 3 == 2]# on-line\n";
        checkPrettyPrintEqual(str);
    }
    
    public void test25If() throws Exception {
        String str = "a = 1 if True else 2\n";
        checkPrettyPrintEqual(str);
    }
    
    public void test25Import() throws Exception {
        String str = "from . import foo\n";
        checkPrettyPrintEqual(str);
    }
    
    public void test25Import2() throws Exception {
        String str = "from ..bar import foo\n";
        checkPrettyPrintEqual(str);
    }
    
    public void test25Import3() throws Exception {
        String str = "from ... import foo\n";
        checkPrettyPrintEqual(str);
    }
    
    public void test25With() throws Exception {
        String str = "" +
                "from __future__ import with_statement\n" +
                "with a:\n" +
                "    print a\n" +
                "";
        checkPrettyPrintEqual(str);
    }
    
    public void test25With2() throws Exception {
        String str = "" +
            "from __future__ import with_statement\n" +
            "with a as b:\n" +
            "    print b\n" +
            "";
        checkPrettyPrintEqual(str);
    }
    
    public void test25With3() throws Exception {
        String str = "" +
        "from __future__ import with_statement\n" +
        "def m1():\n" +
        "    with a as b:\n" +
        "        print b\n" +
        "";
        checkPrettyPrintEqual(str);
    }
    
    
    public void test25With4() throws Exception {
        String str = "" +
        "from __future__ import with_statement\n" +
        "with a:\n" +
        "    callIt1()\n" +
        "    callIt2()\n" +
        "";
        checkPrettyPrintEqual(str);
    }
    
    public void testGlobal() throws Exception {
        String s = ""+
        "global foo\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testImport() throws Exception {
        String s = ""+
        "import foo\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testImport6() throws Exception {
        String s = ""+
        "#foo\n" +
        "from hashlib import md5\n" +
        "new = md5\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testKwArgs2() throws Exception {
        String s = ""+
        "HTTPS(host,None,**(x509 or {}))\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testImport5() throws Exception {
        String s = ""+
        "import foo,bar\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testLambda3() throws Exception {
        String s = ""+
        "lambda a:(1 + 2)\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testLambda4() throws Exception {
        String s = ""+
        "lambda d='':digestmod.new(d)\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    
    public void testComment4() throws Exception {
        String s = ""+
        "class AAA:\n" +
        "    def m1(self):\n" +
        "        pass\n" +
        "#--- barrr\n" +
        "a = 10\n" +
        "#--- fooo" +
        "";
        checkPrettyPrintEqual(s);
        
    }
    public void testComment3() throws Exception {
        String s = ""+
        "class Foo:\n" +
        "    pass\n" +
        "#--- barrr\n" +
        "a = 10\n" +
        "#--- fooo" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testLambda2() throws Exception {
        String s = ""+
        "a = lambda:None\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testDict3() throws Exception {
        String s = ""+
        "d = {#comm1\n" +
        "    1:2}\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testFuncAndComment2() throws Exception {
        String s = ""+
        "class Foo:\n" +
        "    def func1(self):\n" +
        "        pass\n" +
        "    # ------ Head elements\n" +
        "    def func2(self):\n" +
        "        pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testFuncAndComment() throws Exception {
        String s = ""+
        "class Foo:\n" +
        "    def func1(self):pass\n" +
        "    # ------ Head elements\n" +
        "    def func2(self):pass\n" +
        "";
        
        String expected = ""+
        "class Foo:\n" +
        "    def func1(self):\n" +
        "        pass\n" +
        "    # ------ Head elements\n" +
        "    def func2(self):\n" +
        "        pass\n" +
        "";
        checkPrettyPrintEqual(s, expected);
    }
    
    public void testSubscript4() throws Exception {
        String s = ""+
        "print a[b:c()]\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testAssign3() throws Exception {
        String s = ""+
        "a = b = 0\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testComment1() throws Exception {
        String s = ""+
        "del a[-1]#comment\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testArgs() throws Exception {
        String s = ""+
        "def func():\n" +
        "    return a(*b)\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testArgs2() throws Exception {
        String s = ""+
        "def func():\n" +
        "    return a(*(b))\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testListComp4() throws Exception {
        String s = ""+
        "print [e for e in group if e[0] in a]\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testReturn3() throws Exception {
        String s = ""+
        "if a:\n" +
        "    return foo(other)#comment\n" +
        "shouldround = 1\n" +
        "";
        checkPrettyPrintEqual(s);
    }

    public void testAssert() throws Exception {
        String s = ""+
        "assert a not in b\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testStr2() throws Exception {
        String s = ""+
        "r\"foo\"\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testStr() throws Exception {
        String s = ""+
        "a = (r\"a\"#comm1\n" +
        "    r'\"b\"'#comm2\n" +
        "    )\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testAdd() throws Exception {
        String s = ""+
        "m += 'a'\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testWildImport() throws Exception {
        String s = ""+
        "from a import *\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testRaise() throws Exception {
        String s = ""+
            "try:\n" +
            "    pass\n" +
            "except:\n" +
            "    raise SystemError,'err'\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testLambda() throws Exception {
        String s = ""+
        "print lambda n:n\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testListComp3() throws Exception {
        String s = ""+
        "print [s2 for s1 in b for s2 in a]\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testList2() throws Exception {
        String s = ""+
        "print [(a,b)]\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testList3() throws Exception {
        String s = ""+
        "all = [#comm1\n" +
        "    'encode','decode',]\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testListComp2() throws Exception {
        String s = ""+
        "for (raw,cooked) in foo:\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testKwArgs() throws Exception {
        String s = ""+
        "def a(**kwargs):\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    
    public void testTryExcept9() throws Exception {
        String s = ""+
        "def run():\n" +
        "    try:\n" +
        "        exec cmd\n" +
        "    except BdbQuit:\n" +
        "        pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testSubscript3() throws Exception {
        String s = ""+
        "for a in b[:]:\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testEndComments() throws Exception {
        String s = ""+
        "import foo\n" +
        "#end" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testExec() throws Exception {
        String s = "exec cmd in globals,locals\n"+
        "";
        checkPrettyPrintEqual(s);
    }
    public void testTryExcept8() throws Exception {
        String s = ""+
        "try:\n" +
        "    try:\n" +
        "        pass\n" +
        "    except BdbQuit:\n" +
        "        pass\n" +
        "finally:\n" +
        "    self.quitting = 1\n" +
        "    sys.settrace(None)\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testDecorator() throws Exception {
        String s = ""+
        "@decorator1\n" +
        "def m1():\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }

    public void testDecorator3() throws Exception {
        String s = ""+
        "@decorator1\n" +
        "@decorator2\n" +
        "def m1():\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testDecorator2() throws Exception {
        String s = ""+
        "@decorator1(1,*args,**kwargs)\n" +
        "def m1():\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testComment() throws Exception {
        String s = ""+
        "# comment1\n" +
        "# comment2\n" +
        "# comment3\n" +
        "# comment4\n" +
        "'''str'''\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testStarArgs() throws Exception {
        String s = ""+
        "def recv(self,*args):\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testListComp() throws Exception {
        String s = ""+
        "print [x for x in tbinfo]\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testSub() throws Exception {
        String s = ""+
        "print tbinfo[-1]\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testDel() throws Exception {
        String s = ""+
        "del foo\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testPar2() throws Exception {
        String s = ""+
        "def log(self,message):\n" +
        "    sys.stderr.write('log: %s' % str(message))\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testPar() throws Exception {
        String s = ""+
        "print (not connected)\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testSimpleFunc() throws Exception {
        String s = ""+
        "def a():\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testReturn2() throws Exception {
        String s = ""+
        "def writable():\n" +
        "    return (not connected) or len(foo)\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testSubs() throws Exception {
        String s = ""+
        "print num_sent[:512]\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testNot() throws Exception {
        String s = ""+
        "def recv(self,buffer_size):\n" +
        "    data = self.socket.recv(buffer_size)\n" +
        "    if not data:\n" +
        "        pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testIfAnd() throws Exception {
        String s = ""+
        "if aaa and bbb:\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }

    public void testAnd() throws Exception {
        String s = ""+
        "def listen(self,num):\n" +
        "    self.accepting = True\n" +
        "    if os.name == 'nt' and num > 5:\n" +
        "        num = 1\n" +
        "    return self.socket.listen(num)\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testTryExcept7() throws Exception {
        String s = ""+
        "try:\n" +
        "    pass\n" +
        "except select.error,err:\n" +
        "    if False:\n" +
        "        raise \n" +
        "    else:\n" +
        "        return \n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testIf4() throws Exception {
        String s = ""+
        "if map:\n" +
        "    if True:\n" +
        "        time.sleep(timeout)\n" +
        "    else:\n" +
        "        pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testIf3() throws Exception {
        String s = ""+
        "if aaa or bbb:\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testEq() throws Exception {
        String s = ""+
        "if [] == r == w == e:\n" +
        "    time.sleep(timeout)\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testImportAs() throws Exception {
        String s = ""+
        "import foo as bla\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testImportAs2() throws Exception {
        String s = ""+
        "from a import foo as bla\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testIf2() throws Exception {
        String s = ""+
        "def readwrite():\n" +
        "    if True:\n" +
        "        a.b()\n" +
        "    if False:\n" +
        "        pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testBreak() throws Exception {
        String s = ""+
        "for a in b:\n" +
        "    if True:\n" +
        "        break\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testContinue() throws Exception {
        String s = ""+
        "for a in b:\n" +
        "    if True:\n" +
        "        continue\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testBreak2() throws Exception {
        String s = ""+
        "for a in b:\n" +
        "    if True:\n" +
        "        break#comment\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testBreak3() throws Exception {
        String s = ""+
        "for a in b:\n" +
        "    if True:\n" +
        "        #comment1\n" +
        "        break#comment2\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testReturn() throws Exception {
        String s = ""+
        "def a():\n" +
        "    return 0\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testImport2() throws Exception {
        String s = ""+
        "import foo.bla#comment\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testImport3() throws Exception {
        String s = ""+
        "from foo.bla import bla1#comment\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testImport4() throws Exception {
        String s = ""+
        "from foo.bla import bla1\n" +
        "import foo\n" +
        "from bla import (a,b,c)\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testFor() throws Exception {
        String s = "" +
        "for a in b:\n" +
        "    print a\n" +
        "";
        checkPrettyPrintEqual(s);
    }
  
    public void testForElse() throws Exception {
        String s = "" +
        "for a in b:\n" +
        "    print a\n" +
        "else:\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    
    public void testWhile() throws Exception {
        String s = ""+
        "while True:\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testWhile2() throws Exception {
        String s = ""+
        "while ((a + 1 < 0)):#comment\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testWhileElse() throws Exception {
        String s = ""+
        "while True:\n" +
        "    pass\n" +
        "else:\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    

    
    public void testTryExceptRaise() throws Exception {
        String s = ""+
        "try:\n" +
        "    print 'foo'\n" +
        "except:\n" +
        "    raise \n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testTryExcept() throws Exception {
        String s = ""+
        "try:\n" +
        "    print 'foo'\n" +
        "except:\n" +
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testTryExcept2() throws Exception {
        String s = ""+
        "try:\n" +
        "    socket_map\n" +
        "except NameError:\n" +
        "    socket_map = {}\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testTryExcept3() throws Exception {
        String s = ""+
        "try:\n" +
        "    print 'foo'\n" +
        "except (NameError,e):\n" +
        "    print 'err'\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testTryExcept4() throws Exception {
        String s = ""+
        "try:\n" +
        "    print 'foo'\n" +
        "except (NameError,e):\n" +
        "    print 'err'\n" +
        "else:\n" +
        "    print 'else'\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testTryExcept5() throws Exception {
        String s = ""+
        "try:\n" +
        "    print 'foo'\n" +
        "except (NameError,e):\n" +
        "    print 'name'\n" +
        "except (TypeError,e2):\n" +
        "    print 'type'\n" +
        "else:\n" +
        "    print 'else'\n" +
        "";
        checkPrettyPrintEqual(s);
    }

    public void testTryExcept6() throws Exception {
        String s = ""+
        "def read(obj):\n" +
        "    try:\n" +
        "        obj.handle_read_event()\n" +
        "    except:\n" +
        "        obj.handle_error()\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testCall() throws Exception {
        String s = ""+
        "callIt(1)\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testCall2() throws Exception {
        String s = ""+
        "callIt(1#param1\n" +
        ")\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    
    public void testCall3() throws Exception {
        String s = ""+
        "callIt(a=2)\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testCall4() throws Exception {
        String s = ""+
        "callIt(a=2,*args,**kwargs)\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    
    public void testCall5() throws Exception {
        String s = ""+
        "m1(a,#d1\n" +
        "    b,#d2\n" +
        "    c#d3\n" +
        ")\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testIf() throws Exception {
        String s = ""+
        "if i % target == 0:\n"+
        "    pass\n" +
        "";
        checkPrettyPrintEqual(s);
        
    }
    public void testIfElse() throws Exception {
        String s = ""+
        "if True:\n" +
        "    if foo:\n" +
        "        pass\n" +
        "    else:\n" +
        "        pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testListDict() throws Exception {
        String s = ""+
        "a = [1,#this is 1\n" +
        "    2]\n" +
        "a = {1:'foo'}\n" +
        "";
        checkPrettyPrintEqual(s);
        
    }
    
    public void testTupleDict() throws Exception {
        String s = ""+
        "a = (1,#this is 1\n" +
        "    2)\n" +
        "a = {1:'foo'}\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testDict2() throws Exception {
        String s = ""+
        "a = {1:2,#this is 1\n" +
        "    2:2}\n" +
        "a = {1:'foo'}\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testVarious() throws Exception {
        String s = ""+
        "class Foo:\n" +
        "    def __init__(self,a,b):\n" +
        "        print self,#comment0\n" +
        "        a,\n" +
        "        b\n" +
        "    def met1(self,a):#ok comment1\n" +
        "        a,\n" +
        "        b\n" +
        "        class Inner(object):\n" +
        "            pass\n" +
        "        self.met1(a)\n" +
        "print 'ok'\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testYield() throws Exception {
        String s = ""+
        "def foo():\n" +
        "    yield 10\n" +
        "print 'foo'\n" +
        "a = 3\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testYield2() throws Exception {
        String s = ""+
        "def foo():\n" +
        "    yield (10)#comment1\n" +
        "    print 'foo'\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testYield4() throws Exception {
        String s = ""+
        "def foo():\n" +
        "    yield ((a + b) / 2)#comment1\n" +
        "    print 'foo'\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testMultipleBool() throws Exception {
        String s = "X or Y and X and Y or X\n";
        checkPrettyPrintEqual(s);
    }
    public void testFuncComment() throws Exception {
        String s = ""+
        "def foo():\n" +
        "    #comment0\n" +
        "    print 'foo'\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    
    public void testPrint() throws Exception {
        String s = ""+
        "print >> a,'foo'\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testPrintComment() throws Exception {
        String s = ""+
        "def test():#comm1\n" +
        "    print >> (a,#comm2\n" +
        "        'foo')#comm3\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testAttr() throws Exception {
        String s = ""+
        "print a.b\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testAttr2() throws Exception {
        String s = ""+
        "print a.b.c.d\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testAttr3() throws Exception {
        String s = ""+
        "print a.d()\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testAttr4() throws Exception {
        String s = ""+
        "hub.fun#comment\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testAttrCall() throws Exception {
        String s = ""+
        "print a.d().e(1 + 2)\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testSubscript() throws Exception {
        String s = ""+
        "print a[0]\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testSubscript2() throws Exception {
        String s = ""+
        "[0]\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testDefaults() throws Exception {
        String s = ""+
        "def defaults(hi=None):\n" +
        "    if False:\n" +
        "        pass\n" +
        "";
        checkPrettyPrintEqual(s);
        
    }
    public void testDefaults2() throws Exception {
        String s = ""+
        "def defaults(a,x,lo=foo,hi=None):\n" +
        "    if hi is None:\n" +
        "        hi = a\n" +
        "";
        checkPrettyPrintEqual(s);
        
    }
    public void testNoComments() throws Exception {
        String s = ""+
        "class Class1:\n" +
        "    def met1(self,a):\n" +
        "        pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testDocStrings() throws Exception {
        String s = ""+
        "class Class1:\n" +
        "    '''docstring1'''\n" +
        "    a = '''str1'''\n" +
        "    def met1(self,a):\n" +
        "        '''docstring2\n" +
        "        foo\n" +
        "        '''\n" +
        "        pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testDocStrings2() throws Exception {
        String s = ""+
        "class Class1:\n" +
        "    \"\"\"docstring1\"\"\"\n" +
        "    a = 'str1'\n" +
        "    def met1(self,a):\n" +
        "        \"docstring2\"\n" +
        "        ur'unicoderaw'\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testDocStrings3() throws Exception {
        String s = ""+
        "class Class1:\n" +
        "    def met1(self,a):\n" +
        "        ur'unicoderaw' + 'foo'\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testDict() throws Exception {
        String s = ""+
        "if a:\n"+
        "    a = {a:1,b:2,c:3}\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testList() throws Exception {
        String s = ""+
        "if a:\n"+
        "    a = [a,b,c]\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testTuple() throws Exception {
        String s = ""+
        "if a:\n"+
        "    a = (a,b,c)\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testTuple2() throws Exception {
        String s = ""+
        "if a:\n"+
        "    a = (a,b,#comment\n" +
        "        c)\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testIfElse0() throws Exception {
        String s = ""+
        "if a:\n"+
        "    a = 1\n"+
        "elif b:\n"+
        "    b = 2\n"+
        "elif c:\n"+
        "    c = 3#foo\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testIfElse1() throws Exception {
        String s = ""+
        "if a:\n"+
        "    a = 1\n"+
        "elif b:\n"+
        "    b = 2\n"+
        "elif c:\n"+
        "    c = 3\n"+
        "else:\n"+
        "    d = 4\n";
        checkPrettyPrintEqual(s);
    }
    public void testIfElse2() throws Exception {
        String s = ""+
        "if a:\n"+
        "    a = 1#comment1\n"+
        "elif b:\n"+
        "    b = 2#comment2\n"+
        "elif c:\n"+
        "    c = 3#comment3\n"+
        "else:\n"+
        "    d = 4#comment4\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testIfElse3() throws Exception {
        String s = 
        "#commentbefore\n"+      //1
        "if a:#commentIf\n"+     //2
        "    a = 1\n"+           //3
        "elif b:#commentElif\n"+ //4
        "    b = 2\n"+           //5
        "elif c:\n"+             //6
        "    c = 3\n"+           //7
        "else:#commentElse\n"+   //8
        "    d = 4\n" +          //9
        "outOfIf = True\n";      //10
        checkPrettyPrintEqual(s);
    }
    
    public void testCommentAndIf() throws Exception {
        String s = "" +
                "def initiate_send():\n" +
                "    if 10:\n" +
                "        # try to send the buffer\n" +
                "        try:\n" +
                "            num_sent = 10\n" +
                "        except:\n" +
                "            pass\n" +
                "";
        checkPrettyPrintEqual(s);
    }
    

    public void testPlus() throws Exception {
        String s = ""+
        "a = 1 + 1\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testMinus() throws Exception {
        String s = ""+
        "a = 1 - 1\n";
        checkPrettyPrintEqual(s);
    }

    public void testPow() throws Exception {
        String s = ""+
        "a = 1 ** 1\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testLShift() throws Exception {
        String s = ""+
        "a = 1 << 1\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testRShift() throws Exception {
        String s = ""+
        "a = 1 >> 1\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testBitOr() throws Exception {
        String s = ""+
        "a = 1 | 1\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testBitXOr() throws Exception {
        String s = ""+
        "a = 1 ^ 1\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testBitAnd() throws Exception {
        String s = ""+
        "a = 1 & 1\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testFloorDiv() throws Exception {
        String s = ""+
        "a = 1 // 1\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testNoComments2() throws Exception {
        prefs.setSpacesAfterComma(1);
        String s = ""+
        "class Class1(obj1, obj2):\n" +
        "    def met1(self, a, b):\n" +
        "        pass\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testAssign() throws Exception {
        String s = ""+
        "a = 1\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testAssign2() throws Exception {
        String s = ""+
        "a = 1#comment\n";
        checkPrettyPrintEqual(s);
    }
    
    public void testComments1() throws Exception {
        String s = "#comment00\n" +
        "class Class1:#comment0\n" +
        "    #comment1\n" +
        "    def met1(self,a):#comment2\n" +
        "        pass#comment3\n" +
        "";
        checkPrettyPrintEqual(s);
    }
    
    public void testComments2() throws Exception {
        String s = ""+
        "class Foo(object):#test comment\n" +
        "    def m1(self,a,#c1\n" +
        "        b):#c2\n" +
        "        pass\n" +
        "";
        checkPrettyPrintEqual(s);
        
    }
    
    public void testComments3() throws Exception {
        String s = ""+
        "# comment before\n" +
        "i = 0\n" +
        "while (i < 2):# while test comment on-line\n" +
        "    print 'under 5'\n" +
        "    i += 1# augmented assignment on-line\n" +
        "    # this comment disappears\n" +
        "else:# else on-line\n" +
        "    print 'bigger'# print on-line\n" +
        "# after the second body (but actually in the module node)!" +
        "";
        checkPrettyPrintEqual(s);
    }
    

}