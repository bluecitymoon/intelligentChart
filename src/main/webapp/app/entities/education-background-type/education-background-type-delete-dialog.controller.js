(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('EducationBackgroundTypeDeleteController',EducationBackgroundTypeDeleteController);

    EducationBackgroundTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'EducationBackgroundType'];

    function EducationBackgroundTypeDeleteController($uibModalInstance, entity, EducationBackgroundType) {
        var vm = this;

        vm.educationBackgroundType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EducationBackgroundType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
