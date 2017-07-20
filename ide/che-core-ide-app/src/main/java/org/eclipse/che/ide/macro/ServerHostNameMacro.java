/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.macro;

import com.google.common.annotations.Beta;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import org.eclipse.che.api.core.model.workspace.runtime.Server;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.macro.BaseMacro;
import org.eclipse.che.ide.api.macro.Macro;
import org.eclipse.che.ide.api.macro.MacroRegistry;
import org.eclipse.che.ide.api.workspace.model.MachineImpl;

import java.util.Map;
import java.util.Set;

/**
 * Macro which is responsible for the retrieving the hostname (reference) of the registered server.
 * <p>
 * Macro provided: <code>${server.[port].hostname}</code>
 *
 * @author Vlad Zhukovskyi
 * @see AbstractServerMacro
 * @since 4.7.0
 */
@Beta
@Singleton
public class ServerHostNameMacro extends AbstractServerMacro {

    public static final String KEY = "${server.%.hostname}";

    @Inject
    public ServerHostNameMacro(MacroRegistry macroRegistry,
                               EventBus eventBus,
                               AppContext appContext) {
        super(macroRegistry, eventBus, appContext);
    }

    /** {@inheritDoc} */
    @Override
    public Set<Macro> getMacros(MachineImpl devMachine) {
        final Set<Macro> macros = Sets.newHashSet();

        for (Map.Entry<String, ? extends Server> entry : devMachine.getServers().entrySet()) {

            if (Strings.isNullOrEmpty(entry.getValue().getUrl())) {
                continue;
            }

            Macro macro = new BaseMacro(KEY.replace("%", entry.getKey()),
                                        entry.getValue().getUrl(),
                                        "Returns hostname of a server registered by name");

            macros.add(macro);

            // register port without "/tcp" suffix
            if (entry.getKey().endsWith("/tcp")) {
                final String port = entry.getKey().substring(0, entry.getKey().length() - 4);

                Macro shortMacro = new BaseMacro(KEY.replace("%", port),
                                                 entry.getValue().getUrl(),
                                                 "Returns hostname of a server registered by name");

                macros.add(shortMacro);
            }
        }

        return macros;
    }
}
