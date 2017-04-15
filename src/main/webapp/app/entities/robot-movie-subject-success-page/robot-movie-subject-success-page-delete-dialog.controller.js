(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotMovieSubjectSuccessPageDeleteController',RobotMovieSubjectSuccessPageDeleteController);

    RobotMovieSubjectSuccessPageDeleteController.$inject = ['$uibModalInstance', 'entity', 'RobotMovieSubjectSuccessPage'];

    function RobotMovieSubjectSuccessPageDeleteController($uibModalInstance, entity, RobotMovieSubjectSuccessPage) {
        var vm = this;

        vm.robotMovieSubjectSuccessPage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RobotMovieSubjectSuccessPage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
