/*
Copyright 2021 Fausto Spoto

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package io.hotmoka.xodus.env;

import java.util.function.Consumer;
import java.util.function.Function;

import io.hotmoka.exceptions.CheckRunnable;
import io.hotmoka.exceptions.CheckSupplier;
import io.hotmoka.exceptions.UncheckConsumer;
import io.hotmoka.exceptions.UncheckFunction;
import io.hotmoka.exceptions.functions.ConsumerWithExceptions1;
import io.hotmoka.exceptions.functions.ConsumerWithExceptions2;
import io.hotmoka.exceptions.functions.ConsumerWithExceptions3;
import io.hotmoka.exceptions.functions.ConsumerWithExceptions4;
import io.hotmoka.exceptions.functions.ConsumerWithExceptions5;
import io.hotmoka.exceptions.functions.ConsumerWithExceptions6;
import io.hotmoka.exceptions.functions.ConsumerWithExceptions7;
import io.hotmoka.exceptions.functions.FunctionWithExceptions1;
import io.hotmoka.exceptions.functions.FunctionWithExceptions2;
import io.hotmoka.exceptions.functions.FunctionWithExceptions3;
import io.hotmoka.exceptions.functions.FunctionWithExceptions4;
import io.hotmoka.exceptions.functions.FunctionWithExceptions5;
import io.hotmoka.exceptions.functions.FunctionWithExceptions6;
import io.hotmoka.exceptions.functions.FunctionWithExceptions7;
import io.hotmoka.xodus.ExodusException;
import jetbrains.exodus.env.StoreConfig;

/**
 * An adaptor of a Xodus environment.
 */
public class Environment {
	private final jetbrains.exodus.env.Environment parent;

	/**
	 * Creates a Xodus environment at the given directory.
	 * 
	 * @param dir the directory
	 * @throws ExodusException if the creation fails
	 */
	public Environment(String dir) throws ExodusException {
		try {
			this.parent = jetbrains.exodus.env.Environments.newInstance(dir);
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
	 * Creates a Xodus environment at the given directory with the given configuration.
	 * 
	 * @param dir the directory
	 * @param config the configuration
	 * @throws ExodusException if the creation fails
	 */
	public Environment(String dir, EnvironmentConfig config) throws ExodusException {
		try {
			this.parent = jetbrains.exodus.env.Environments.newInstance(dir, config.toNative());
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
	 * Closes this environment.
	 * 
	 * @throws ExodusException if the closure fails
	 */
	public void close() throws ExodusException {
		try {
			parent.close();
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
	 * Starts a new transaction.
	 * 
	 * @return the new transaction
	 * @throws ExodusException if the operation fails
	 */
	public Transaction beginTransaction() throws ExodusException {
		try {
			return new Transaction(parent.beginTransaction());
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @throws ExodusException if the operation fails
     */
	public void executeInTransaction(Consumer<Transaction> executable) throws ExodusException {
		try {
			parent.executeInTransaction(txn -> executable.accept(new Transaction(txn)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
	 * @param <E> the only checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception the class tag of {@code E}
	 * @throws ExodusException if the operation fails
	 * @throws E if the {@code executable} throws this type of exception
     */
	public <E extends Throwable> void executeInTransaction(Class<E> exception, ConsumerWithExceptions1<Transaction, E> executable) throws ExodusException, E {
		try {
			CheckRunnable.check(exception, () -> parent.executeInTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E> uncheck(exception, txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
	 * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable> void executeInTransaction(Class<E1> exception1, Class<E2> exception2, ConsumerWithExceptions2<Transaction, E1, E2> executable) throws ExodusException, E1, E2 {
		try {
			CheckRunnable.check(exception1, exception2, () -> parent.executeInTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2> uncheck(exception1, exception2, txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable> void executeInTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3,
					ConsumerWithExceptions3<Transaction, E1, E2, E3> executable) throws ExodusException, E1, E2, E3 {

		try {
			CheckRunnable.check(exception1, exception2, exception3, () -> parent.executeInTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3> uncheck(exception1, exception2, exception3,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E4> a fourth checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
	 * @throws E4 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable> void executeInTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4,
					ConsumerWithExceptions4<Transaction, E1, E2, E3, E4> executable) throws ExodusException, E1, E2, E3, E4 {
		try {
			CheckRunnable.check(exception1, exception2, exception3, exception4, () -> parent.executeInTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3, E4> uncheck(exception1, exception2, exception3, exception4,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E4> a fourth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E5> a fifth checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
	 * @throws E4 if the {@code executable} throws this type of exception
	 * @throws E5 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable> void executeInTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5,
					ConsumerWithExceptions5<Transaction, E1, E2, E3, E4, E5> executable) throws ExodusException, E1, E2, E3, E4, E5 {
		try {
			CheckRunnable.check(exception1, exception2, exception3, exception4, exception5, () -> parent.executeInTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3, E4, E5> uncheck(exception1, exception2, exception3, exception4, exception5,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E4> a fourth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E5> a fifth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E6> a sixth checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @param exception6 the class tag of {@code E6}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
	 * @throws E4 if the {@code executable} throws this type of exception
	 * @throws E5 if the {@code executable} throws this type of exception
	 * @throws E6 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable> void executeInTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5, Class<E6> exception6,
					ConsumerWithExceptions6<Transaction, E1, E2, E3, E4, E5, E6> executable) throws ExodusException, E1, E2, E3, E4, E5, E6 {
		try {
			CheckRunnable.check(exception1, exception2, exception3, exception4, exception5, exception6, () -> parent.executeInTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3, E4, E5, E6> uncheck(exception1, exception2, exception3, exception4, exception5, exception6,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E4> a fourth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E5> a fifth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E6> a sixth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E7> a seventh checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @param exception6 the class tag of {@code E6}
	 * @param exception7 the class tag of {@code E7}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
	 * @throws E4 if the {@code executable} throws this type of exception
	 * @throws E5 if the {@code executable} throws this type of exception
	 * @throws E6 if the {@code executable} throws this type of exception
	 * @throws E7 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable, E7 extends Throwable> void executeInTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5, Class<E6> exception6, Class<E7> exception7,
					ConsumerWithExceptions7<Transaction, E1, E2, E3, E4, E5, E6, E7> executable) throws ExodusException, E1, E2, E3, E4, E5, E6, E7 {
		try {
			CheckRunnable.check(exception1, exception2, exception3, exception4, exception5, exception6, exception7, () -> parent.executeInTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3, E4, E5, E6, E7> uncheck(exception1, exception2, exception3, exception4, exception5, exception6, exception7,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new read-only transaction, only once,
     * since the transaction is read-only and is never flushed.
     *
     * @param executable transactional executable
     * @throws ExodusException if the operation fails
     */
	public void executeInReadonlyTransaction(Consumer<Transaction> executable) throws ExodusException {
		try {
			parent.executeInReadonlyTransaction(txn -> executable.accept(new Transaction(txn)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new read-only transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
	 * @param <E> the only checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception the class tag of {@code E}
	 * @throws ExodusException if the operation fails
	 * @throws E if the {@code executable} throws this type of exception
     */
	public <E extends Throwable> void executeInReadonlyTransaction(Class<E> exception, ConsumerWithExceptions1<Transaction, E> executable) throws ExodusException, E {
		try {
			CheckRunnable.check(exception, () -> parent.executeInReadonlyTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E> uncheck(exception, txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new read-only transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
	 * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable> void executeInReadonlyTransaction(Class<E1> exception1, Class<E2> exception2, ConsumerWithExceptions2<Transaction, E1, E2> executable) throws ExodusException, E1, E2 {
		try {
			CheckRunnable.check(exception1, exception2, () -> parent.executeInReadonlyTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2> uncheck(exception1, exception2, txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new read-only transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable> void executeInReadonlyTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3,
					ConsumerWithExceptions3<Transaction, E1, E2, E3> executable) throws ExodusException, E1, E2, E3 {

		try {
			CheckRunnable.check(exception1, exception2, exception3, () -> parent.executeInReadonlyTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3> uncheck(exception1, exception2, exception3,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new read-only transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E4> a fourth checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
	 * @throws E4 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable> void executeInReadonlyTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4,
					ConsumerWithExceptions4<Transaction, E1, E2, E3, E4> executable) throws ExodusException, E1, E2, E3, E4 {
		try {
			CheckRunnable.check(exception1, exception2, exception3, exception4, () -> parent.executeInReadonlyTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3, E4> uncheck(exception1, exception2, exception3, exception4,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new read-only transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E4> a fourth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E5> a fifth checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
	 * @throws E4 if the {@code executable} throws this type of exception
	 * @throws E5 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable> void executeInReadonlyTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5,
					ConsumerWithExceptions5<Transaction, E1, E2, E3, E4, E5> executable) throws ExodusException, E1, E2, E3, E4, E5 {
		try {
			CheckRunnable.check(exception1, exception2, exception3, exception4, exception5, () -> parent.executeInReadonlyTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3, E4, E5> uncheck(exception1, exception2, exception3, exception4, exception5,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new read-only transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E4> a fourth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E5> a fifth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E6> a sixth checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @param exception6 the class tag of {@code E6}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
	 * @throws E4 if the {@code executable} throws this type of exception
	 * @throws E5 if the {@code executable} throws this type of exception
	 * @throws E6 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable> void executeInReadonlyTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5, Class<E6> exception6,
					ConsumerWithExceptions6<Transaction, E1, E2, E3, E4, E5, E6> executable) throws ExodusException, E1, E2, E3, E4, E5, E6 {
		try {
			CheckRunnable.check(exception1, exception2, exception3, exception4, exception5, exception6, () -> parent.executeInReadonlyTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3, E4, E5, E6> uncheck(exception1, exception2, exception3, exception4, exception5, exception6,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new read-only transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E4> a fourth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E5> a fifth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E6> a sixth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E7> a seventh checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @param exception6 the class tag of {@code E6}
	 * @param exception7 the class tag of {@code E7}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
	 * @throws E4 if the {@code executable} throws this type of exception
	 * @throws E5 if the {@code executable} throws this type of exception
	 * @throws E6 if the {@code executable} throws this type of exception
	 * @throws E7 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable, E7 extends Throwable> void executeInReadonlyTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5, Class<E6> exception6, Class<E7> exception7,
					ConsumerWithExceptions7<Transaction, E1, E2, E3, E4, E5, E6, E7> executable) throws ExodusException, E1, E2, E3, E4, E5, E6, E7 {
		try {
			CheckRunnable.check(exception1, exception2, exception3, exception4, exception5, exception6, exception7, () -> parent.executeInReadonlyTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3, E4, E5, E6, E7> uncheck(exception1, exception2, exception3, exception4, exception5, exception6, exception7,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new exclusive transaction, only once,
     * since the transaction is exclusive. Only read-only transactions can occur concurrently.
     *
     * @param executable transactional executable
     * @throws ExodusException if the operation fails
     */
	public void executeInExclusiveTransaction(Consumer<Transaction> executable) throws ExodusException {
		try {
			parent.executeInExclusiveTransaction(txn -> executable.accept(new Transaction(txn)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new exclusive transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
	 * @param <E> the only checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception the class tag of {@code E}
	 * @throws ExodusException if the operation fails
	 * @throws E if the {@code executable} throws this type of exception
     */
	public <E extends Throwable> void executeInExclusiveTransaction(Class<E> exception, ConsumerWithExceptions1<Transaction, E> executable) throws ExodusException, E {
		try {
			CheckRunnable.check(exception, () -> parent.executeInExclusiveTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E> uncheck(exception, txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new exclusive transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
	 * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable> void executeInExclusiveTransaction(Class<E1> exception1, Class<E2> exception2, ConsumerWithExceptions2<Transaction, E1, E2> executable) throws ExodusException, E1, E2 {
		try {
			CheckRunnable.check(exception1, exception2, () -> parent.executeInExclusiveTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2> uncheck(exception1, exception2, txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new exclusive transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable> void executeInExclusiveTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3,
					ConsumerWithExceptions3<Transaction, E1, E2, E3> executable) throws ExodusException, E1, E2, E3 {

		try {
			CheckRunnable.check(exception1, exception2, exception3, () -> parent.executeInExclusiveTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3> uncheck(exception1, exception2, exception3,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new exclusive transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E4> a fourth checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
	 * @throws E4 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable> void executeInExclusiveTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4,
					ConsumerWithExceptions4<Transaction, E1, E2, E3, E4> executable) throws ExodusException, E1, E2, E3, E4 {
		try {
			CheckRunnable.check(exception1, exception2, exception3, exception4, () -> parent.executeInExclusiveTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3, E4> uncheck(exception1, exception2, exception3, exception4,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new exclusive transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E4> a fourth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E5> a fifth checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
	 * @throws E4 if the {@code executable} throws this type of exception
	 * @throws E5 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable> void executeInExclusiveTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5,
					ConsumerWithExceptions5<Transaction, E1, E2, E3, E4, E5> executable) throws ExodusException, E1, E2, E3, E4, E5 {
		try {
			CheckRunnable.check(exception1, exception2, exception3, exception4, exception5, () -> parent.executeInExclusiveTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3, E4, E5> uncheck(exception1, exception2, exception3, exception4, exception5,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new exclusive transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E4> a fourth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E5> a fifth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E6> a sixth checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @param exception6 the class tag of {@code E6}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
	 * @throws E4 if the {@code executable} throws this type of exception
	 * @throws E5 if the {@code executable} throws this type of exception
	 * @throws E6 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable> void executeInExclusiveTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5, Class<E6> exception6,
					ConsumerWithExceptions6<Transaction, E1, E2, E3, E4, E5, E6> executable) throws ExodusException, E1, E2, E3, E4, E5, E6 {
		try {
			CheckRunnable.check(exception1, exception2, exception3, exception4, exception5, exception6, () -> parent.executeInExclusiveTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3, E4, E5, E6> uncheck(exception1, exception2, exception3, exception4, exception5, exception6,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Executes the specified executable in a new exclusive transaction. If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param executable the transactional executable
     * @param <E1> a first checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E2> a second checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E3> a third checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E4> a fourth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E5> a fifth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E6> a sixth checked exception that is allowed to be thrown by the {@code executable}
	 * @param <E7> a seventh checked exception that is allowed to be thrown by the {@code executable}
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @param exception6 the class tag of {@code E6}
	 * @param exception7 the class tag of {@code E7}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code executable} throws this type of exception
	 * @throws E2 if the {@code executable} throws this type of exception
	 * @throws E3 if the {@code executable} throws this type of exception
	 * @throws E4 if the {@code executable} throws this type of exception
	 * @throws E5 if the {@code executable} throws this type of exception
	 * @throws E6 if the {@code executable} throws this type of exception
	 * @throws E7 if the {@code executable} throws this type of exception
     */
	public <E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable, E7 extends Throwable> void executeInExclusiveTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5, Class<E6> exception6, Class<E7> exception7,
					ConsumerWithExceptions7<Transaction, E1, E2, E3, E4, E5, E6, E7> executable) throws ExodusException, E1, E2, E3, E4, E5, E6, E7 {
		try {
			CheckRunnable.check(exception1, exception2, exception3, exception4, exception5, exception6, exception7, () -> parent.executeInExclusiveTransaction
				(x -> UncheckConsumer.<jetbrains.exodus.env.Transaction, E1, E2, E3, E4, E5, E6, E7> uncheck(exception1, exception2, exception3, exception4, exception5, exception6, exception7,
						txn -> executable.accept(new Transaction(txn))).accept(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new read-only transaction,
     * only once, since the transaction is read-only and is never flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param computable the transactional computable
     * @return the result of {@code computable}
     * @throws ExodusException if the operation fails
     */
	public <T> T computeInReadonlyTransaction(Function<Transaction, T> computable) throws ExodusException {
		try {
			return parent.computeInReadonlyTransaction(txn -> computable.apply(new Transaction(txn)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new read-only transaction,
     * only once, since the transaction is read-only and is never flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E> the only checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception the class tag of {@code E}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E if the {@code computable} throws this type of exception
     */
	public <T, E extends Throwable> T computeInReadonlyTransaction(Class<E> exception, FunctionWithExceptions1<Transaction, T, E> computable) throws ExodusException, E {
		try {
			return CheckSupplier.check(exception, () -> parent.computeInReadonlyTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E> uncheck(exception, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new read-only transaction,
     * only once, since the transaction is read-only and is never flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable> T computeInReadonlyTransaction
			(Class<E1> exception1, Class<E2> exception2, FunctionWithExceptions2<Transaction, T, E1, E2> computable) throws ExodusException, E1, E2 {

		try {
			return CheckSupplier.check(exception1, exception2, () -> parent.computeInReadonlyTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2> uncheck(exception1, exception2, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new read-only transaction,
     * only once, since the transaction is read-only and is never flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable> T computeInReadonlyTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3,
					FunctionWithExceptions3<Transaction, T, E1, E2, E3> computable) throws ExodusException, E1, E2, E3 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, () -> parent.computeInReadonlyTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3> uncheck(exception1, exception2, exception3, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new read-only transaction,
     * only once, since the transaction is read-only and is never flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param <E4> the fourth checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
	 * @throws E4 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable> T computeInReadonlyTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4,
					FunctionWithExceptions4<Transaction, T, E1, E2, E3, E4> computable) throws ExodusException, E1, E2, E3, E4 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, exception4, () -> parent.computeInReadonlyTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3, E4> uncheck(exception1, exception2, exception3, exception4, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new read-only transaction,
     * only once, since the transaction is read-only and is never flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param <E4> the fourth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E5> the fifth checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
	 * @throws E4 if the {@code computable} throws this type of exception
	 * @throws E5 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable> T computeInReadonlyTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5,
					FunctionWithExceptions5<Transaction, T, E1, E2, E3, E4, E5> computable) throws ExodusException, E1, E2, E3, E4, E5 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, exception4, exception5, () -> parent.computeInReadonlyTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3, E4, E5> uncheck(exception1, exception2, exception3, exception4, exception5, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new read-only transaction,
     * only once, since the transaction is read-only and is never flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param <E4> the fourth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E5> the fifth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E6> the sixth checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @param exception6 the class tag of {@code E6}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
	 * @throws E4 if the {@code computable} throws this type of exception
	 * @throws E5 if the {@code computable} throws this type of exception
	 * @throws E6 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable> T computeInReadonlyTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5, Class<E6> exception6,
					FunctionWithExceptions6<Transaction, T, E1, E2, E3, E4, E5, E6> computable) throws ExodusException, E1, E2, E3, E4, E5, E6 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, exception4, exception5, exception6, () -> parent.computeInReadonlyTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3, E4, E5, E6> uncheck(exception1, exception2, exception3, exception4, exception5, exception6, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new read-only transaction,
     * only once, since the transaction is read-only and is never flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param <E4> the fourth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E5> the fifth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E6> the sixth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E7> the seventh checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @param exception6 the class tag of {@code E6}
	 * @param exception7 the class tag of {@code E7}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
	 * @throws E4 if the {@code computable} throws this type of exception
	 * @throws E5 if the {@code computable} throws this type of exception
	 * @throws E6 if the {@code computable} throws this type of exception
	 * @throws E7 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable, E7 extends Throwable> T computeInReadonlyTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5, Class<E6> exception6, Class<E7> exception7,
					FunctionWithExceptions7<Transaction, T, E1, E2, E3, E4, E5, E6, E7> computable) throws ExodusException, E1, E2, E3, E4, E5, E6, E7 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, exception4, exception5, exception6, exception7, () -> parent.computeInReadonlyTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3, E4, E5, E6, E7> uncheck(exception1, exception2, exception3, exception4, exception5, exception6, exception7, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param computable the transactional computable
     * @return the result of the computable
     * @throws ExodusException if the operation fails
     */
	public <T> T computeInTransaction(Function<Transaction, T> computable) throws ExodusException {
		try {
			return parent.computeInTransaction(txn -> computable.apply(new Transaction(txn)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E> the only checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception the class tag of {@code E}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E if the {@code computable} throws this type of exception
     */
	public <T, E extends Throwable> T computeInTransaction(Class<E> exception, FunctionWithExceptions1<Transaction, T, E> computable) throws ExodusException, E {
		try {
			return CheckSupplier.check(exception, () -> parent.computeInTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E> uncheck(exception, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable> T computeInTransaction
			(Class<E1> exception1, Class<E2> exception2, FunctionWithExceptions2<Transaction, T, E1, E2> computable) throws ExodusException, E1, E2 {

		try {
			return CheckSupplier.check(exception1, exception2, () -> parent.computeInTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2> uncheck(exception1, exception2, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable> T computeInTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3,
					FunctionWithExceptions3<Transaction, T, E1, E2, E3> computable) throws ExodusException, E1, E2, E3 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, () -> parent.computeInTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3> uncheck(exception1, exception2, exception3, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param <E4> the fourth checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
	 * @throws E4 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable> T computeInTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4,
					FunctionWithExceptions4<Transaction, T, E1, E2, E3, E4> computable) throws ExodusException, E1, E2, E3, E4 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, exception4, () -> parent.computeInTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3, E4> uncheck(exception1, exception2, exception3, exception4, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param <E4> the fourth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E5> the fifth checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
	 * @throws E4 if the {@code computable} throws this type of exception
	 * @throws E5 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable> T computeInTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5,
					FunctionWithExceptions5<Transaction, T, E1, E2, E3, E4, E5> computable) throws ExodusException, E1, E2, E3, E4, E5 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, exception4, exception5, () -> parent.computeInTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3, E4, E5> uncheck(exception1, exception2, exception3, exception4, exception5, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param <E4> the fourth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E5> the fifth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E6> the sixth checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @param exception6 the class tag of {@code E6}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
	 * @throws E4 if the {@code computable} throws this type of exception
	 * @throws E5 if the {@code computable} throws this type of exception
	 * @throws E6 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable> T computeInTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5, Class<E6> exception6,
					FunctionWithExceptions6<Transaction, T, E1, E2, E3, E4, E5, E6> computable) throws ExodusException, E1, E2, E3, E4, E5, E6 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, exception4, exception5, exception6, () -> parent.computeInTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3, E4, E5, E6> uncheck(exception1, exception2, exception3, exception4, exception5, exception6, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * If the transaction cannot be flushed
     * at its end, the executable is executed once more until the transaction is finally flushed.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param <E4> the fourth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E5> the fifth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E6> the sixth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E7> the seventh checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @param exception6 the class tag of {@code E6}
	 * @param exception7 the class tag of {@code E6}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
	 * @throws E4 if the {@code computable} throws this type of exception
	 * @throws E5 if the {@code computable} throws this type of exception
	 * @throws E6 if the {@code computable} throws this type of exception
	 * @throws E7 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable, E7 extends Throwable> T computeInTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5, Class<E6> exception6, Class<E7> exception7,
					FunctionWithExceptions7<Transaction, T, E1, E2, E3, E4, E5, E6, E7> computable) throws ExodusException, E1, E2, E3, E4, E5, E6, E7 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, exception4, exception5, exception6, exception7, () -> parent.computeInTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3, E4, E5, E6, E7> uncheck(exception1, exception2, exception3, exception4, exception5, exception6, exception7, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * The transaction is executed only once, since it is exclusive. Only read-only
     * transactions can occur concurrently.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param computable the transactional computable
     * @return the result of the computable
     * @throws ExodusException if the operation fails
     */
	public <T> T computeInExclusiveTransaction(Function<Transaction, T> computable) throws ExodusException {
		try {
			return parent.computeInExclusiveTransaction(txn -> computable.apply(new Transaction(txn)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * The transaction is executed only once, since it is exclusive. Only read-only
     * transactions can occur concurrently.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E> the only checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception the class tag of {@code E}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E if the {@code computable} throws this type of exception
     */
	public <T, E extends Throwable> T computeInExclusiveTransaction(Class<E> exception, FunctionWithExceptions1<Transaction, T, E> computable) throws ExodusException, E {
		try {
			return CheckSupplier.check(exception, () -> parent.computeInExclusiveTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E> uncheck(exception, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * The transaction is executed only once, since it is exclusive. Only read-only
     * transactions can occur concurrently.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable> T computeInExclusiveTransaction
			(Class<E1> exception1, Class<E2> exception2, FunctionWithExceptions2<Transaction, T, E1, E2> computable) throws ExodusException, E1, E2 {

		try {
			return CheckSupplier.check(exception1, exception2, () -> parent.computeInExclusiveTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2> uncheck(exception1, exception2, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * The transaction is executed only once, since it is exclusive. Only read-only
     * transactions can occur concurrently.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable> T computeInExclusiveTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3,
					FunctionWithExceptions3<Transaction, T, E1, E2, E3> computable) throws ExodusException, E1, E2, E3 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, () -> parent.computeInExclusiveTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3> uncheck(exception1, exception2, exception3, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * The transaction is executed only once, since it is exclusive. Only read-only
     * transactions can occur concurrently.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param <E4> the fourth checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
	 * @throws E4 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable> T computeInExclusiveTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4,
					FunctionWithExceptions4<Transaction, T, E1, E2, E3, E4> computable) throws ExodusException, E1, E2, E3, E4 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, exception4, () -> parent.computeInExclusiveTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3, E4> uncheck(exception1, exception2, exception3, exception4, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * The transaction is executed only once, since it is exclusive. Only read-only
     * transactions can occur concurrently.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param <E4> the fourth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E5> the fifth checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
	 * @throws E4 if the {@code computable} throws this type of exception
	 * @throws E5 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable> T computeInExclusiveTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5,
					FunctionWithExceptions5<Transaction, T, E1, E2, E3, E4, E5> computable) throws ExodusException, E1, E2, E3, E4, E5 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, exception4, exception5, () -> parent.computeInExclusiveTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3, E4, E5> uncheck(exception1, exception2, exception3, exception4, exception5, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * The transaction is executed only once, since it is exclusive. Only read-only
     * transactions can occur concurrently.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param <E4> the fourth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E5> the fifth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E6> the sixth checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @param exception6 the class tag of {@code E6}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
	 * @throws E4 if the {@code computable} throws this type of exception
	 * @throws E5 if the {@code computable} throws this type of exception
	 * @throws E6 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable> T computeInExclusiveTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5, Class<E6> exception6,
					FunctionWithExceptions6<Transaction, T, E1, E2, E3, E4, E5, E6> computable) throws ExodusException, E1, E2, E3, E4, E5, E6 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, exception4, exception5, exception6, () -> parent.computeInExclusiveTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3, E4, E5, E6> uncheck(exception1, exception2, exception3, exception4, exception5, exception6, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Computes and returns a value by calling the specified computable in a new transaction.
     * The transaction is executed only once, since it is exclusive. Only read-only
     * transactions can occur concurrently.
     *
     * @param <T> the type of the value returned by {@code computable}
     * @param <E1> the first checked exception that is allowed to be thrown by the {@code computable}
     * @param <E2> the second checked exception that is allowed to be thrown by the {@code computable}
     * @param <E3> the third checked exception that is allowed to be thrown by the {@code computable}
     * @param <E4> the fourth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E5> the fifth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E6> the sixth checked exception that is allowed to be thrown by the {@code computable}
     * @param <E7> the seventh checked exception that is allowed to be thrown by the {@code computable}
     * @param computable the transactional computable
	 * @param exception1 the class tag of {@code E1}
	 * @param exception2 the class tag of {@code E2}
	 * @param exception3 the class tag of {@code E3}
	 * @param exception4 the class tag of {@code E4}
	 * @param exception5 the class tag of {@code E5}
	 * @param exception6 the class tag of {@code E6}
	 * @param exception7 the class tag of {@code E7}
	 * @return the result of {@code computable}
	 * @throws ExodusException if the operation fails
	 * @throws E1 if the {@code computable} throws this type of exception
	 * @throws E2 if the {@code computable} throws this type of exception
	 * @throws E3 if the {@code computable} throws this type of exception
	 * @throws E4 if the {@code computable} throws this type of exception
	 * @throws E5 if the {@code computable} throws this type of exception
	 * @throws E6 if the {@code computable} throws this type of exception
	 * @throws E7 if the {@code computable} throws this type of exception
     */
	public <T, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable, E5 extends Throwable, E6 extends Throwable, E7 extends Throwable> T computeInExclusiveTransaction
			(Class<E1> exception1, Class<E2> exception2, Class<E3> exception3, Class<E4> exception4, Class<E5> exception5, Class<E6> exception6, Class<E7> exception7,
					FunctionWithExceptions7<Transaction, T, E1, E2, E3, E4, E5, E6, E7> computable) throws ExodusException, E1, E2, E3, E4, E5, E6, E7 {

		try {
			return CheckSupplier.check(exception1, exception2, exception3, exception4, exception5, exception6, exception7, () -> parent.computeInExclusiveTransaction
				(x -> UncheckFunction.<jetbrains.exodus.env.Transaction, T, E1, E2, E3, E4, E5, E6, E7> uncheck(exception1, exception2, exception3, exception4, exception5, exception6, exception7, txn -> computable.apply(new Transaction(txn))).apply(x)));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Opens an existing or creates a new store with the specified name, inside the given transaction.
     * It does not allow duplicated keys and supports efficient sequential access of keys.
     *
     * @param name the name of the store
     * @param txn the transaction used to create store
     * @return the resulting store instance
     * @throws ExodusException if the operation fails
     */
	public Store openStoreWithoutDuplicates(String name, Transaction txn) throws ExodusException {
		try {
			return new Store(parent.openStore(name, StoreConfig.WITHOUT_DUPLICATES, txn.toNative()));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Opens an existing or creates a new store with the specified name, inside the given transaction.
     * It does not allow duplicated keys and supports efficient random look-up of keys.
     *
     * @param name the name of the store
     * @param txn the transaction used to create store
     * @return the resulting store instance
     * @throws ExodusException if the operation fails
     */
	public Store openStoreWithoutDuplicatesWithPrefixing(String name, Transaction txn) throws ExodusException {
		try {
			return new Store(parent.openStore(name, StoreConfig.WITHOUT_DUPLICATES_WITH_PREFIXING, txn.toNative()));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Opens an existing or creates a new store with the specified name, inside the given transaction.
     * It allows duplicated keys and supports efficient sequential access of keys.
     *
     * @param name the name of the store
     * @param txn the transaction used to create store
     * @return the resulting store instance
     * @throws ExodusException if the operation fails
     */
	public Store openStoreWithDuplicates(String name, Transaction txn) throws ExodusException {
		try {
			return new Store(parent.openStore(name, StoreConfig.WITH_DUPLICATES, txn.toNative()));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}

	/**
     * Opens an existing or creates a new store with the specified name, inside the given transaction.
     * It allows duplicated keys and supports efficient random look-up of keys.
     *
     * @param name the name of the store
     * @param txn the transaction used to create store
     * @return the resulting store instance
     * @throws ExodusException if the operation fails
     */
	public Store openStoreWithDuplicatesWithPrefixing(String name, Transaction txn) throws ExodusException {
		try {
			return new Store(parent.openStore(name, StoreConfig.WITH_DUPLICATES_WITH_PREFIXING, txn.toNative()));
		}
		catch (jetbrains.exodus.ExodusException e) {
			throw new ExodusException(e);
		}
	}
}