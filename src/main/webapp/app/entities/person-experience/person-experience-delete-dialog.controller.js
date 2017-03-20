(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonExperienceDeleteController',PersonExperienceDeleteController);

    PersonExperienceDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonExperience'];

    function PersonExperienceDeleteController($uibModalInstance, entity, PersonExperience) {
        var vm = this;

        vm.personExperience = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonExperience.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
