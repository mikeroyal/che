/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.ide.notification;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import org.vectomatic.dom.svg.ui.SVGResource;

/**
 * Notifications resources. Contains definition of styles and icons.
 *
 * @author Andrey Plotnikov
 * @author Vlad Zhukovskyi
 */
public interface NotificationResources extends ClientBundle {
  interface NotificationCss extends CssResource {

    String notificationPanel();

    String notificationPanelContainer();

    String notification();

    String notificationIconWrapper();

    String notificationContentWrapper();

    String notificationTitleWrapper();

    String notificationMessageWrapper();

    String notificationCloseButtonWrapper();

    String notificationStatusProgress();

    String notificationStatusSuccess();

    String notificationStatusFail();

    String notificationStatusWarning();

    String notificationPopup();

    String notificationPopupContentWrapper();

    String notificationPopupIconWrapper();

    String notificationPopupCloseButtonWrapper();

    String notificationPopupTitleWrapper();

    String notificationPopupMessageWrapper();

    String notificationPopupPanel();

    String notificationPopupPlaceholder();

    String notificationShowingAnimation();

    String notificationHidingAnimation();
  }

  @Source({"notification.css", "org/eclipse/che/ide/api/ui/style.css"})
  NotificationCss notificationCss();

  @Source("success.svg")
  SVGResource success();

  @Source("fail.svg")
  SVGResource fail();

  @Source("progress.svg")
  SVGResource progress();

  @Source("warning.svg")
  SVGResource warning();
}
