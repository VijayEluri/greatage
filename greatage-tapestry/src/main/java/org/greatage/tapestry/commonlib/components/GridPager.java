/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractGridComponent;
import org.greatage.tapestry.grid.CenteredPagesBuilder;
import org.greatage.tapestry.grid.Page;
import org.greatage.tapestry.grid.PagesBuilder;
import org.greatage.tapestry.grid.PaginationModel;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class GridPager extends AbstractGridComponent {

	@Parameter
	private PagesBuilder builder;

	private List<Page> pages;

	private Page page;

	@Component(
			parameters = {
					"overrides=overrides",
					"class=prop:pagerClass"},
			inheritInformalParameters = true)
	private Menu menu;

	@Component(
			parameters = {
					"overrides=overrides",
					"selected=currentPage"})
	private MenuItem menuItem;

	@Component(
			parameters = {
					"event=changePage",
					"context=value",
					"disabled=disabled"})
	private GridLink pageLink;

	protected PagesBuilder defaultBuilder() {
		return new CenteredPagesBuilder();
	}

	public List<Page> getPages() {
		if (pages == null) {
			pages = builder.build(getPaginationModel());
		}
		return pages;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getPagerClass() {
		return CSSConstants.TOOLBAR_MENU_CLASS;
	}

	public String getLabel() {
		return getMessages().format(page.getType().name(), page.getValue(getPaginationModel()));
	}

	public int getValue() {
		return page.getValue(getPaginationModel());
	}

	public boolean isDisabled() {
		return page.isDisabled(getPaginationModel());
	}

	public boolean isCurrentPage() {
		final PaginationModel model = getPaginationModel();
		return page.getType() == Page.PageType.PAGE && page.getValue(model) == model.getCurrentPage();
	}
}