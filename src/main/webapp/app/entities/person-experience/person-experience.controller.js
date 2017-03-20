(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonExperienceController', PersonExperienceController);

    PersonExperienceController.$inject = ['$scope', '$state', 'PersonExperience', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', '$stateParams'];

    function PersonExperienceController ($scope, $state, PersonExperience, ParseLinks, AlertService, paginationConstants, pagingParams, $stateParams) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        var personId = $stateParams.person_id;
        if (personId) {
            loadAllByPersonId();
        } else {

            loadAll();
        }

        function loadAll () {
            PersonExperience.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.personExperiences = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        function loadAllByPersonId () {
            PersonExperience.loadAllByPersonId({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort(),
                id: personId
            }, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.personExperiences = data;
                vm.page = pagingParams.page;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
