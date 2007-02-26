package org.python.pydev.refactoring.ast.adapters;

import java.util.ArrayList;
import java.util.List;

import org.python.pydev.parser.jython.ast.argumentsType;
import org.python.pydev.parser.jython.ast.exprType;
import org.python.pydev.refactoring.ast.visitors.VisitorFactory;

public class FunctionArgAdapter extends AbstractNodeAdapter<argumentsType> {

	public FunctionArgAdapter(ModuleAdapter module, FunctionDefAdapter parent,
			argumentsType node) {
		super(module, parent, node);
	}

	public boolean hasKwArg() {
		return getASTNode().kwarg != null;
	}

	public boolean hasVarArg() {
		return (getASTNode().vararg != null);
	}

	public boolean hasArg() {
		return (getASTNode().args != null) && (getASTNode().args.length > 0);
	}

	public List<String> getArgOnly() {
		List<String> args = new ArrayList<String>();
		for (exprType arg : getASTNode().args) {
			args.add(nodeHelper.getName(arg));
		}
		return args;
	}

	public List<String> getSelfFilteredArgNames() {
		List<String> args = new ArrayList<String>();
		for (exprType arg : getSelfFilteredArgs()) {
			args.add(nodeHelper.getName(arg));
		}
		return args;
	}

	public List<exprType> getSelfFilteredArgs() {
		List<exprType> args = new ArrayList<exprType>();
		if (getASTNode().args == null)
			return args;

		for (exprType arg : getASTNode().args) {
			String argument = nodeHelper.getName(arg);
			if (!nodeHelper.isSelf(argument))
				args.add(arg);
		}
		return args;
	}

	public boolean isEmptyArgument() {
		return (!hasArg()) && (!(hasVarArg())) && (!(hasKwArg()));
	}

	public boolean hasOnlySelf() {
		return getSelfFilteredArgs().size() == 0 && (!(hasVarArg()))
				&& (!(hasKwArg()));
	}

	public String getSignature() {
		return VisitorFactory.createSourceFromAST(this.getASTNode(), true);
	}
}