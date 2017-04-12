(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotController', RobotController);

    RobotController.$inject = ['$scope', '$state', 'Robot', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function RobotController ($scope, $state, Robot, ParseLinks, AlertService, paginationConstants, pagingParams) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();

        function loadAll () {
            Robot.query({
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
                vm.robots = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        vm.robotRunning = false;

        vm.startRobot = function (robot) {

            vm.robotRunning = true;
            Robot.start({id: robot.id}).$promise.then(function (data) {

                vm.robotRunning = false;
                
            }, function (error) {

                vm.robotRunning = false;
            })
        };

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
