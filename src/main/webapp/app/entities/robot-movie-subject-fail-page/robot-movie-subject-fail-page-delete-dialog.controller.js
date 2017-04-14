(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotMovieSubjectFailPageDeleteController',RobotMovieSubjectFailPageDeleteController);

    RobotMovieSubjectFailPageDeleteController.$inject = ['$uibModalInstance', 'entity', 'RobotMovieSubjectFailPage'];

    function RobotMovieSubjectFailPageDeleteController($uibModalInstance, entity, RobotMovieSubjectFailPage) {
        var vm = this;

        vm.robotMovieSubjectFailPage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RobotMovieSubjectFailPage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
